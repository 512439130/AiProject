package com.fcbox.locker.ai.project

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcbox.locker.ai.project.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 移除了之前会导致顶部出现白色空隙的 setOnApplyWindowInsetsListener
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val orders = mutableListOf<Order>()
        for (i in 1..20) {
            val isCompleted = i % 2 == 0
            orders.add(
                Order(
                    id = "20231027000$i",
                    time = "2023-10-27 10:${if (i < 10) "0$i" else i}",
                    price = "￥${99 * i}.00",
                    status = if (isCompleted) getString(R.string.order_status_completed) else getString(R.string.order_status_pending),
                    statusColor = if (isCompleted) Color.parseColor("#4CAF50") else Color.parseColor("#FF9800"),
                    address = "广东省深圳市南山区软件产业基地${i}栋${i}层",
                    items = listOf(
                        OrderItem("智能运动手表", "规格：午夜黑", "￥599.00", 1, R.drawable.ic_orders_active),
                        OrderItem("快速充电头", "规格：30W/白色", "￥79.00", 2, R.drawable.ic_orders_outline)
                    )
                )
            )
        }

        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = OrderAdapter(orders)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
