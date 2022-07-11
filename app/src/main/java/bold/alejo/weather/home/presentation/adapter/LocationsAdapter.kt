package bold.alejo.weather.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bold.alejo.weather.databinding.LocationItemBinding
import bold.alejo.weather.home.domain.model.Location
import bold.alejo.weather.home.presentation.listener.LocationsListener
import bold.alejo.weather.utils.View.setSafeOnClickListener

class LocationsAdapter(val listener: LocationsListener) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    var locations: List<Location> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(locations[position])

    override fun getItemCount(): Int = locations.size

    inner class ViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.apply {
                location.also { (name, country) ->
                    tvCity.text = name
                    tvCountry.text = country
                }
            }
            itemView.setSafeOnClickListener { listener.onLocationClicked(location) }
        }
    }
}
