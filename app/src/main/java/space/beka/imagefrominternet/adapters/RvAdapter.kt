package space.beka.imagefrominternet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.beka.imagefrominternet.databinding.ItemRvBinding
import space.beka.imagefrominternet.models.Urls

class RvAdapter(var list: List<Urls>) : RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemTvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemTvBinding.root) {
        fun onBind(urls: Urls, position: Int) {
            itemTvBinding.first.text = urls.login
            itemTvBinding.second.text = urls.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}