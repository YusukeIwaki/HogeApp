package jp.co.crowdworks.hogeapp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;


/**
 * TestClass for MainActivity.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;

    public MainActivityTest() {
        super(MainActivity.class /*テストしたいActivityのクラスオブジェクト*/);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        getInstrumentation().waitForIdleSync(); // UIスレッドに順番がまわってくるまで、じっと待つ
    }

    @Override
    public void tearDown() throws Exception {
        mMainActivity = null;
        super.tearDown();
    }

    public void testButton1isActivated() throws Exception {
        final View v = mMainActivity.findViewById(R.id.activity_main_btn_test1);
        assertTrue("R.id.activity_main_btn_test1 のIDを持つViewがButtonであることを確認する"
                , v instanceof Button);

        final Button btn = (Button) v;
        assertTrue("ボタン(activity_main_btn_test1)が有効であることを確認する"
                , btn.isEnabled());
    }

    public void testButton1CallbackDialog() throws Exception {
        // Espressoで記述.

        onView(withId(R.id.activity_main_btn_test1)).perform(click());

        // ダイアログ表示が行われていることを確認.
        onView(withText("This application will be quited. Are you sure?"))
                .check(matches(isDisplayed()));

        // 選択肢はYes/Noか？
        onView(withText("Yes"))
                .check(matches(isDisplayed()));
        onView(withText("No"))
                .check(matches(isDisplayed()));

        // 選択肢っぽいものは、ほかに画面に存在していてはいけない！
        onView(anyOf(withText("OK"), withText("Cancel")))
                .check(doesNotExist());
    }
}
