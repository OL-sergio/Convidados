package mvvm.roomdatabase.aplicacaoconvidados.view.todos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mvvm.roomdatabase.aplicacaoconvidados.R
import mvvm.roomdatabase.aplicacaoconvidados.databinding.FragmentAllGuestsBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.GuestConstants
import mvvm.roomdatabase.aplicacaoconvidados.view.adapter.GuestAdapter
import mvvm.roomdatabase.aplicacaoconvidados.view.guest.GuestFormActivity
import mvvm.roomdatabase.aplicacaoconvidados.view.listener.GuestListener


class AllGuestsFragment: Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null

    private lateinit var mAllGuestsViewModel: AllGuestsViewModel
    private var mListener: GuestListener? = null
    private val mAdapter: GuestAdapter = GuestAdapter()



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       mAllGuestsViewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recyclerview
        // 1 - Create one Recyclerview
        val allGuestsRecyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_all_guests)
        // 2 - Define one Layout
        allGuestsRecyclerView.layoutManager = LinearLayoutManager(context)
        // 3 - Define one Adapter
        allGuestsRecyclerView.adapter = mAdapter

        mListener = object : GuestListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(GuestConstants.GUEST_ID ,id)
                intent.putExtras(bundle)
                startActivity(intent)

            }
        }

        mAdapter.attachListener(mListener!!)

        observer()

        return root
    }

    override fun onResume() {
        mAllGuestsViewModel.loadRepository()
        super.onResume()
    }

    private fun observer() {
        mAllGuestsViewModel.guestList.observe(viewLifecycleOwner) {
            mAdapter.updateGuest(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}