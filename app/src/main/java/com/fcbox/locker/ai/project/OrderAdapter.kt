package com.fcbox.locker.ai.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcbox.locker.ai.project.databinding.ItemOrderBinding
import com.fcbox.locker.ai.project.databinding.ItemOrderDetailBinding

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val context = holder.binding.root.context
        
        holder.binding.tvOrderId.text = "${context.getString(R.string.order_no)}${order.id}"
        holder.binding.tvOrderTime.text = "${context.getString(R.string.order_time_label)}${order.time}"
        holder.binding.tvOrderPrice.text = "${context.getString(R.string.order_price_label)}${order.price}"
        holder.binding.tvOrderStatus.text = order.status
        holder.binding.tvOrderStatus.setTextColor(order.statusColor)
        holder.binding.tvOrderAddress.text = "${context.getString(R.string.order_address_label)}${order.address}"

        // Expand/Collapse logic
        holder.binding.llExpandArea.visibility = if (order.isExpanded) View.VISIBLE else View.GONE
        holder.binding.ivExpandArrow.rotation = if (order.isExpanded) 180f else 0f

        holder.binding.root.setOnClickListener {
            order.isExpanded = !order.isExpanded
            notifyItemChanged(position)
        }

        // Inner RecyclerView for items
        holder.binding.rvItems.layoutManager = LinearLayoutManager(context)
        holder.binding.rvItems.adapter = OrderItemAdapter(order.items)
    }

    override fun getItemCount() = orders.size
}

class OrderItemAdapter(private val items: List<OrderItem>) : RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemOrderDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvItemName.text = item.name
        holder.binding.tvItemSpec.text = item.spec
        holder.binding.tvItemPrice.text = item.price
        holder.binding.tvItemCount.text = "x${item.count}"
        holder.binding.ivItemIcon.setImageResource(item.iconRes)
    }

    override fun getItemCount() = items.size
}
