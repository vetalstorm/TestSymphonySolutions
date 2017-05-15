package freelancer.testsymphonysolutions;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ErrorDialog extends DialogFragment implements View.OnClickListener {
    public static String ERROR_TYPE = "error_type";
    public static String ERROR_MESSAGE = "error_message";

    public static final int ACTIVITY_MAIN_SERVER_ERROR = 10;
    public static final int ACTIVITY_TEAMS_SERVER_ERROR = 11;

    public static final int ACTIVITY_MAIN_CONNECTION_ERROR = 20;
    public static final int ACTIVITY_TEAMS_CONNECTION_ERROR = 21;

    private int errorCode;
    private String errorMessage = "";

    private OnResultActionForActivities listener;

    public static ErrorDialog newInstance(int error, String errorMessage) {
        Bundle args = new Bundle();
        args.putInt(ERROR_TYPE, error);
        args.putString(ERROR_MESSAGE, errorMessage);
        ErrorDialog dialog = new ErrorDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
        errorCode = getArguments().getInt(ERROR_TYPE, -1);
        errorMessage = getArguments().getString(ERROR_MESSAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_critical_error, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View root) {
        TextView tvActionOne = (TextView) root.findViewById(R.id.tvActionOne);
        tvActionOne.setOnClickListener(this);

        TextView tvActionTwo = (TextView) root.findViewById(R.id.tvActionTwo);
        tvActionTwo.setOnClickListener(this);

        TextView tvErrorMessage = (TextView) root.findViewById(R.id.tvErrorMessage);

        TextView tvErrorTitle = (TextView) root.findViewById(R.id.tvErrorTitle);

        switch (errorCode) {
            case ACTIVITY_MAIN_SERVER_ERROR:
            case ACTIVITY_TEAMS_SERVER_ERROR:
                tvErrorMessage.setText(errorMessage);
                break;
            case ACTIVITY_MAIN_CONNECTION_ERROR:
            case ACTIVITY_TEAMS_CONNECTION_ERROR:
                tvErrorTitle.setText(getString(R.string.error_dialog_title_no_connection));
                tvErrorMessage.setText(getString(R.string.error_dialog_message_no_connection));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvActionOne:
                switch (errorCode ) {
                    case ACTIVITY_MAIN_SERVER_ERROR:
                    case ACTIVITY_TEAMS_SERVER_ERROR:
                    case ACTIVITY_MAIN_CONNECTION_ERROR:
                    case ACTIVITY_TEAMS_CONNECTION_ERROR:
                        listener.actionOne(errorCode);
                        getDialog().dismiss();
                        break;
                    default:
                        getDialog().dismiss();
                        break;
                }
                break;
            case R.id.tvActionTwo:
                switch (errorCode ) {
                    case ACTIVITY_MAIN_SERVER_ERROR:
                    case ACTIVITY_TEAMS_SERVER_ERROR:
                    case ACTIVITY_MAIN_CONNECTION_ERROR:
                    case ACTIVITY_TEAMS_CONNECTION_ERROR:
                        listener.actionTwo(errorCode);
                        getDialog().dismiss();
                        break;

                 default:
                        getDialog().dismiss();
                        break;
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnResultActionForActivities) context;
        } catch (ClassCastException ignored) {}
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnResultActionForActivities {
        void actionOne(int errorType);
        void actionTwo(int errorType);
    }
}

