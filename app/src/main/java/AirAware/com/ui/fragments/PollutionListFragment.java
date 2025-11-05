package AirAware.com.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import AirAware.com.databinding.FragmentPollutionListBinding;
import AirAware.com.ui.adapters.PollutionAdapter;
import AirAware.com.viewmodel.AirQualityViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment pour afficher la liste des données de pollution
 */
@AndroidEntryPoint
public class PollutionListFragment extends Fragment {

    private FragmentPollutionListBinding binding;
    private AirQualityViewModel viewModel;
    private PollutionAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AirQualityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPollutionListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        observeViewModel();

        // Les données sont chargées par MainActivity lors de la sélection de la ville
    }

    private void setupRecyclerView() {
        adapter = new PollutionAdapter();
        binding.recyclerViewPollution.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewPollution.setAdapter(adapter);
    }

    private void observeViewModel() {
        // Observer les données de qualité de l'air
        viewModel.getAirQualityData().observe(getViewLifecycleOwner(), airQualityList -> {
            if (airQualityList != null && !airQualityList.isEmpty()) {
                adapter.submitList(airQualityList);
                binding.recyclerViewPollution.setVisibility(View.VISIBLE);
                binding.textViewEmpty.setVisibility(View.GONE);
            } else {
                binding.recyclerViewPollution.setVisibility(View.GONE);
                binding.textViewEmpty.setVisibility(View.VISIBLE);
            }
        });

        // Observer l'état de chargement
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Observer les erreurs
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
