package com.kweku.googlefitsampleapp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes.FITNESS_ACTIVITY_READ
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

import dagger.Module
import dagger.Provides

@Module
object GoogleFitModule {


   private val fitnessOptions: FitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

  @Provides
  fun provideFitnessOptions(): FitnessOptions{
      return fitnessOptions
  }

    @Provides
    fun providesGoogleSignOptions(): GoogleSignInOptions{
       return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .addExtension(fitnessOptions)
            .requestScopes(Scope(FITNESS_ACTIVITY_READ))
            .build()
    }

    @Provides
    fun providesGoogleSignIn(context: Context ): GoogleSignInAccount{

        return  GoogleSignIn.getAccountForExtension(context, fitnessOptions)
    }

}