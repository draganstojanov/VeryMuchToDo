package com.andraganoid.verymuchtodo.shortVersion.ui.stack

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.shortVersion.main.MainViewModel
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.andraganoid.verymuchtodo.shortVersion.util.hideKeyboard
import com.andraganoid.verymuchtodo.shortVersion.util.logX
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StackFragment : Fragment() {

    private var _binding: StackFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()

    private var stack: ArrayList<TodoList?> = arrayListOf()
    private var topModalHeight = 0
    private val ANIMATION_DURATION = 300L

    private var isNewList = false

    //    private var title = ""
//    private var description = ""
    var listForEdit: TodoList = TodoList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = StackFragmentBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topModal.doOnLayout { initCollapse() }
    }

    private fun setup() {
        val adapter = StackAdapter(this)
        binding.stacksRecView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSnapshotState().collect { tlState ->
                    when (tlState) {
                        is StackState.Stack -> {
                            logX(2, tlState.stacks)
                            stack = tlState.stacks
                            adapter.stackList = stack
                        }
                        is StackState.Error -> bottomToast(tlState.errorMsg)
                        else -> {
                        }
                    }
                }
            }
        }

        //  binding.createNewList.setOnClickListener { testlist[0]?.let { it1 -> viewModel.updateListTest(it1) } }//TODO TEST

        binding.createNewList.setOnClickListener { //openTodoListEditor(TodoList())
            if (!binding.topModalWrapper.isVisible) {
                listForEdit = TodoList(title = "", description = "", id = "id-${System.currentTimeMillis()}")
                binding.listTitle.setText(listForEdit.title)
                binding.listDescription.setText(listForEdit.description)
                expandStackEdit()
                isNewList = true
            }
        }


        binding.cancelBtn.setOnClickListener {
            hideKeyboard()
            collapseStackEdit()
        }

        binding.submitBtn.setOnClickListener {
            //todo save
            hideKeyboard()
            collapseStackEdit()

            listForEdit.title = binding.listTitle.text.toString()
            listForEdit.description = binding.listDescription.text.toString()

            if (isNewList) {
                viewModel.addList(listForEdit)
            } else {
                viewModel.updateList(listForEdit)
            }
        }


    }

    fun listSelected(id: String) {
        viewModel.selectedListId = id
        findNavController().navigate(R.id.todoListFragment)
    }

    fun openTodoListEditor(tl: TodoList) {
        //  StackEditDialog(tl).show(requireActivity().supportFragmentManager, StackEditDialog::class.simpleName)

        listForEdit = tl

        binding.listTitle.setText(tl.title)
        binding.listDescription.setText(tl.description)
        expandStackEdit()
        isNewList = false
    }


    private fun expandStackEdit() {

        binding.topModalWrapper.isVisible = true
        binding.overlay.isVisible = true
        (ValueAnimator.ofInt(0, topModalHeight)).apply {
            duration = ANIMATION_DURATION
            addUpdateListener { animation ->
                binding.topModal.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModal.apply {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    requestLayout()
                }
            }
            start()
        }

    }

    private fun collapseStackEdit() {
        collapse(ANIMATION_DURATION)
    }


    private fun initCollapse() {
        topModalHeight = binding.topModal.height
        collapse(1)
    }

    private fun collapse(animationDuration: Long) {
        (ValueAnimator.ofInt(topModalHeight, 0)).apply {
            duration = animationDuration
            addUpdateListener { animation ->
                binding.topModal.apply {
                    layoutParams.height = animation.animatedValue as Int
                    requestLayout()
                }
            }
            doOnEnd {
                binding.topModalWrapper.isVisible = false
                binding.overlay.isVisible = false
            }
            start()
        }
    }


}


