package com.rzl.movapp.core.ui


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rzl.movapp.core.databinding.ItemHomeBinding
import com.rzl.movapp.core.domain.model.Popular
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private val popularList = ArrayList<Popular>()
    var onItemClick: ((Popular) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setAllPopularList(popularResponse: List<Popular>?) {
        if (popularResponse != null) {
            popularList.clear()
            popularList.addAll(popularResponse)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var loadImageTask: LoadImageTask? = null

        fun bind(allPopular: Popular) {
            with(binding) {
                tvTitle.text = allPopular.title
                tvReleaseDate.text = allPopular.releaseDate

                val imageUrl = "https://image.tmdb.org/t/p/w185/${allPopular.posterPath}"
                loadImageTask?.cancel(true)
                loadImageTask = LoadImageTask(imgPoster)
                loadImageTask?.execute(imageUrl)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(popularList[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = popularList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = popularList.size

    private class LoadImageTask(imageView: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {

        private val imageViewRef: WeakReference<ImageView> = WeakReference(imageView)

        override fun doInBackground(vararg params: String?): Bitmap? {
            try {
                val url = URL(params[0])
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                return BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            imageViewRef.get()?.let { imageView ->
                if (result != null) {
                    imageView.setImageBitmap(result)
                }
            }
        }
    }

}




