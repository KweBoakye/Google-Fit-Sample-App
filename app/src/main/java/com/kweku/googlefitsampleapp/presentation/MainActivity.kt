package com.kweku.googlefitsampleapp.presentation

import android.Manifest
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.material.button.MaterialButton
import com.kweku.googlefitsampleapp.*
import com.kweku.googlefitsampleapp.databinding.ActivityMainBinding
import com.kweku.googlefitsampleapp.domain.HistoryInteractor
import com.kweku.googlefitsampleapp.domain.StepCountDataSet
import org.threeten.bp.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.runCatching

class MainActivity : AppCompatActivity() {

    enum class FitActionRequestCode {
        READ_DATA,
        UPDATE_AND_READ_DATA,
        DELETE_DATA
    }

    private val isRunningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    private val manifestPermission = ACTIVITY_RECOGNITION

    var MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 0

    @Inject
    lateinit var historyInteractor: HistoryInteractor

    @Inject
    lateinit var mainViewModelProviderFactory: MainViewModelProviderFactory

    @Inject
    lateinit var googleSignInOptions: GoogleSignInOptions

    @Inject
    lateinit var fitnessOptions: FitnessOptions

    @Inject
    lateinit var googleSignInAccount: GoogleSignInAccount

    lateinit var googleSignInClient: GoogleSignInClient

     lateinit var mainViewModel: MainViewModel
    private lateinit var loadButton : MaterialButton
    private lateinit var barChart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).mainComponent.inject(this)

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        mainViewModel = ViewModelProvider(this, mainViewModelProviderFactory)
            .get(MainViewModel::class.java)

    val activityMainBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val rootView: View = activityMainBinding.root

        loadButton = activityMainBinding.loadButton
        loadButton.text = "Load Steps this Week"
        barChart = activityMainBinding.barChart
        setUpChart()
        barChart.xAxis.valueFormatter =
            DateFormatter()


        setContentView(rootView)
    }

    fun chart(stepCountDataSets: List<StepCountDataSet>){

        val entries: MutableList<BarEntry> = mutableListOf()

        for ( stepCountDataSet in stepCountDataSets){

            val x =  runCatching {
                    stepCountDataSet
                        .stepCountDataPoints[0].startTime.toLocalDate().toEpochDay()
                        .toFloat()}.getOrDefault(0f)

            Timber.i("x= $x")

            val y =  runCatching {   stepCountDataSet
                .stepCountDataPoints[0].dataPoint
                .toFloat()}.getOrDefault(0f)

            Timber.i("y = $y")

            entries.add(BarEntry( x, y))
        }

        val barDataSet: BarDataSet = BarDataSet(entries, "BarDataSet")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        val data: BarData = BarData(barDataSet)
        data.barWidth = 0.9f
        data.setValueFormatter(DateFormatter())
        barChart.data = data
        barChart.setFitBars(true)

        barChart.invalidate()
    }

    private fun setUpChart(){
        with(barChart){

            xAxis.axisMinimum =LocalDate.now()
                .minusWeeks(1)
                .toEpochDay().toFloat()

            xAxis.mAxisMaximum =LocalDate.now()
                .toEpochDay().toFloat()
            setPinchZoom(false)
            val description = Description()
            description.text = "Step Count For The Last 7 Days"
            setDescription(description)
            xAxis.labelRotationAngle = 90f



        }

    }
    override fun onStart() {
        super.onStart()
        signInAndRun(FitActionRequestCode.READ_DATA)
        mainViewModel.observestepCountDatasetLiveData(this,this::chart)
        setLoadButtonOnclickListener()
    }

    private fun setLoadButtonOnclickListener(){
        loadButton.setOnClickListener{
            historyInteractor.getData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {
                val postSignInAction = FitActionRequestCode.values()[requestCode]
                postSignInAction.let {

                }
            }
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
       Timber.i(message)
    }

    fun signInAndRun(requestCode: FitActionRequestCode){
        if (oAuthPermissionsApproved()){

        } else {
            GoogleSignIn.requestPermissions(
                this,
                requestCode.ordinal,
                googleSignInAccount,
                fitnessOptions
            )
        }

    }

    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(googleSignInAccount, fitnessOptions)



    fun checkIsActivityRecognitionPermisionApproved():Boolean{

        return when (isRunningQOrLater) {
            true -> {
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                )}
            false  -> {true}
        }
    }

    fun requestPermission(){

        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACTIVITY_RECOGNITION)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,

                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION)
        }
    }

    }

