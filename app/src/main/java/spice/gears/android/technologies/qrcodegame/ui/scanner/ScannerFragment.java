package spice.gears.android.technologies.qrcodegame.ui.scanner;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.Nullable;
        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProviders;

        import spice.gears.android.technologies.qrcodegame.R;

public class ScannerFragment extends Fragment {

    private ScannerVievModel ScanerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScanerViewModel =
                ViewModelProviders.of(this).get(ScannerVievModel.class);
        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        final TextView textView = root.findViewById(R.id.text_QRscanner);
        ScanerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}