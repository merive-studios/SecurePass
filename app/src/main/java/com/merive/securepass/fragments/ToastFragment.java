package com.merive.securepass.fragments;

import static com.merive.securepass.elements.TypingTextView.typingAnimation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.merive.securepass.MainActivity;
import com.merive.securepass.R;
import com.merive.securepass.elements.TypingTextView;

public class ToastFragment extends Fragment {

    TypingTextView text;

    /**
     * This method is setting ToastFragment Arguments for making a message.
     *
     * @param message Message value.
     * @return ToastFragment with necessary arguments.
     */
    public static ToastFragment newInstance(String message) {
        ToastFragment frag = new ToastFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    /**
     * This method is creating ToastFragment.
     *
     * @param inflater           Needs for getting Fragment View.
     * @param parent             Argument of inflater.inflate().
     * @param savedInstanceState Saving Fragment Values.
     * @return Fragment View.
     * @see View
     * @see Bundle
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toast, parent, false);
    }

    /**
     * This method is executing after Fragment View was created.
     * In this method will be initializing layout variables,
     * setting message to TypingTextView.
     * Fragment will replace after 5.75 seconds to BarFragment.
     *
     * @param view               Fragment View Value.
     * @param savedInstanceState Saving Fragment Values.
     * @see View
     * @see Bundle
     * @see TypingTextView
     * @see BarFragment
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MainActivity.toast = true;
        initVariables(view);
        setText();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            ((MainActivity) getActivity()).openBarFragment();
            MainActivity.toast = false;
        }, 4250);
    }

    /**
     * This method is initializing layout variables.
     *
     * @param view Needs for finding elements on Layout.
     * @see View
     */
    private void initVariables(View view) {
        text = view.findViewById(R.id.toast_text);
    }

    /**
     * This method is setting message value to TypingTextView.
     */
    private void setText() {
        typingAnimation(text, getArguments().getString("message"));
    }
}

