package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;

import static com.google.common.truth.Truth.assertThat;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class HeightActivityUnitTest {
    private static final String TAG = "HeightActivityUnitTest";
    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, TestFitnessService::new);
        intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);
        intent.putExtra("FITNESS_SERVICE_KEY", TEST_SERVICE);
    }

    @Test
    public void testUpdateMiles() {
        nextStepCount = 0;

        ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textMiles = activity.findViewById(R.id.miles);
            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("0.0");
            Log.d(TAG, "testUpdateMiles: ");
            nextStepCount = 1337;

            SharedPreferences sharedPreferences = androidx.test.core.app.ApplicationProvider.getApplicationContext().getSharedPreferences(
                            "USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat("steps_per_mile", 100);
            editor.apply();

            activity.setStepCount(nextStepCount);
            assertThat(textMiles.getText().toString()).isEqualTo("13.37");
        });
    }

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private HomeActivity homeActivity;

        public TestFitnessService(HomeActivity homeActivity) {
            this.homeActivity = homeActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            homeActivity.setStepCount(nextStepCount);
        }
    }
}