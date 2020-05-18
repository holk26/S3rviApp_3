package com.hcabreraar.s3rviapp

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vdx.designertoast.DesignerToast
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
            //Glide.with(context).load(user.imageUrl).into(itemView.circleImag)
            Picasso.get().load(user.imagenUrlUs).placeholder(R.drawable.persona).into(itemView.circleImag)
            itemView.nombreEmpresaXml.text = user.nombreUs
            itemView.servicioXml.text = user.profesionUs
            itemView.ciudadXml.text = user.ciudadUs
            var userid3 = user.idUsuarioUs


            itemView.cardView.setOnClickListener {
               // DesignerToast.Success(context, userid3, Gravity.CENTER, Toast.LENGTH_SHORT)

                val intent = Intent(context.applicationContext, ViewPerfil::class.java)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("ID", userid3)
                context.startActivity(intent)

            }

            }//fin bindView

         init{

        }
        }


    }
