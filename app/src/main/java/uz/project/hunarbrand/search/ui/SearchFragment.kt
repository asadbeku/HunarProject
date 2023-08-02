package uz.project.hunarbrand.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import uz.project.hunarbrand.R
import uz.project.hunarbrand.databinding.FragmentSearchBinding
import uz.project.hunarbrand.search.adapter.SearchAdapter
import uz.project.hunarbrand.search.view_model.SearchViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchAdapter :SearchAdapter? = null
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observerSearchItem()
        observerFindItem()
        findItem()
    }

    private fun initList(){
        searchViewModel.getUsers()
        val linearLayout = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        searchAdapter = SearchAdapter {
            Toast.makeText(requireContext(), "Clicked position: $it", Toast.LENGTH_LONG).show()
        }

        with(binding.searchRecycler){
            adapter = searchAdapter
            layoutManager= linearLayout
            setHasFixedSize(true)
        }
    }

    private fun findItem(){

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is being changed
                val enteredText = s.toString()
                searchViewModel.findUsers(enteredText)
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has changed
            }
        }

        binding.searchText.addTextChangedListener(textWatcher)
    }

    private fun observerSearchItem(){
        searchViewModel.usersList.observe(viewLifecycleOwner){
            searchAdapter?.items = it
        }
    }

    private fun observerFindItem(){
        searchViewModel.findCategoryList.observe(viewLifecycleOwner){
            searchAdapter?.items = it
        }
    }
}