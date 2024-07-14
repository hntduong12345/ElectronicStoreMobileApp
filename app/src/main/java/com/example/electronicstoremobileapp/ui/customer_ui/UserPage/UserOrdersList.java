package com.example.electronicstoremobileapp.ui.customer_ui.UserPage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.electronicstoremobileapp.Adapters.order.CustomerOrderAdapter;
import com.example.electronicstoremobileapp.Adapters.product.ProductAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.admins.ui.orders.viewElements.OrderAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentUserOrdersListBinding;
import com.example.electronicstoremobileapp.models.PagingResponseDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

import kotlin.Triple;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOrdersList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrdersList extends Fragment {

    FragmentUserOrdersListBinding binding;
    public NavController navController;

    List<OrderDto> orderList;

    CustomerOrderAdapter adapter;

    Context currentContext;
    NavHostFragment parentFragment;

    public UserOrdersList() {
        // Required empty public constructor
    }


    public static UserOrdersList newInstance() {
        UserOrdersList fragment = new UserOrdersList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserOrdersListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.parentFragment = (NavHostFragment) getParentFragment();

        currentContext = this.getContext();

        orderList = new ArrayList<>();
        fecthData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_userOrdersList_to_userPageFragment);
                }
            }
        });

//        binding.lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
//                if (parentFragment != null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("Order", orderList.get(position));
//                    NavController navController = parentFragment.getNavController();
//                    navController.navigate(R.id.action_userOrdersList_to_orderDetailFragment, bundle);
//                }
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }

    private void fecthData(){

        Triple<String, String, String> userInfo = UserLoggingUtil.GetUserInfo(getContext());

        Call<List<OrderDto>> call = ApiClient.getServiceClient(OrderServices.class).GetOrdersByAccount(userInfo.component1());
        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth orders with status code " + code);
                    return;
                }
                orderList.clear();;
                orderList.addAll(response.body());
                adapter = new CustomerOrderAdapter(currentContext, orderList, R.layout.listviewtitem_user_order_list_item);
                binding.lvOrderList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth orders");
                return;
            }
        });
    }
}