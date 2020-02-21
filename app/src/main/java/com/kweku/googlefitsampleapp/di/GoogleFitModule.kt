package com.kweku.googlefitsampleapp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import dagger.Module
import dagger.Provides

@Module
object GoogleFitModule {


    lateinit var fitnessOptions: FitnessOptions

  @Provides
  fun provideFitnessOptions(): FitnessOptions{



      if (!::fitnessOptions.isInitialized){
      fitnessOptions = FitnessOptions.builder()
          .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
          .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
          .build()}
      return fitnessOptions
  }

    @Provides
    fun providesGoogleSignIn(context: Context ): GoogleSignInAccount{
        return  GoogleSignIn.getAccountForExtension(context, fitnessOptions)
    }


}