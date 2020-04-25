package com.hcabreraar.s3rviapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_fragmento_inicio.view.*

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.MainViwHolder>() {

    private var datalist = mutableListOf<Blog>()
    //lateinit var onItemClick: (Any) -> Unit




    fun setListData(data:MutableList<Blog>){
        datalist = data

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViwHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_fragmento_inicio,parent,false)
        return MainViwHolder(view)

    }

    override fun getItemCount(): Int {
        return if (datalist.size > 0){
            datalist.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: MainViwHolder, position: Int) {
        val user = datalist[position]
        holder.bindView(user)
    }

    inner class MainViwHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindView(user:Blog){
            Glide.with(context).load(user.imageUrl).into(itemView.circleImag)
            itemView.text_title.text = user.nombreUs
            itemView.servicioXml.text = "Servicio: "+user.servicioUs
            itemView.ciudadXml.text = "Ubicacion: "+user.ciudadUs
            itemView.telefonoXml.text = "Telefono: "+user.telefonUs




            itemView.setOnClickListener {

            }

            }//fin bindView

         init{

        }
        }


    }
