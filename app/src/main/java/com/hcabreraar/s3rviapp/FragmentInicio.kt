package com.hcabreraar.s3rviapp

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout


class FragmentInicio : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var shimer : ShimmerFrameLayout
    private lateinit var adapter: MainAdapter

    companion object {
        fun newInstance() = FragmentInicio()
    }

    private lateinit var viewModel: FragmentInicioViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentInicioViewModel::class.java)
        // TODO: Use the ViewModel



        //val listView: View? = activity?.findViewById(R.id.action_perfil)
        //listView?.visibility = View.VISIBLE


        recyclerView = view!!.findViewById(R.id.view_servicios)
        shimer = view!!.findViewById(R.id.shimmer_view_container)



        adapter = MainAdapter(requireContext())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())  //en vez de this contexto
        recyclerView.adapter = adapter

        observeData()

        shimer.startShimmer()
        viewModel.run {
            fetchUserData().observe(viewLifecycleOwner, Observer {
                shimer.stopShimmer()
                shimer.visibility = View.GONE
                adapter.setListData(it)
                adapter.notifyDataSetChanged()


            })
        }

    }

    fun  observeData(){




    }

    fun clickPerron(){
        Toast.makeText(context, "melos", Toast.LENGTH_SHORT).show()



    }

}