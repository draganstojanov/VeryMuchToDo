package com.andraganoid.verymuchtodo.ktodo.stack.stacks

import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.databinding.StacksFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Stack
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.stack.StacksViewModel
import kotlinx.android.synthetic.main.stacks_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class StacksFragment : TodoBaseFragment() {

    private lateinit var binding: StacksFragmentBinding
    private val viewModel: StacksViewModel by sharedViewModel()


    var toggle = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StacksFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StacksAdapter(this);
        viewModel.allStacks.observe(viewLifecycleOwner, Observer { stacks -> adapter.stackList = stacks })

        topModalWrapper.setOnClickListener {
              if (toggle) {
                  expandStackEdit()
               } else {
            collapseStackEdit()
              }
               toggle = !toggle
        }

    }

    fun stackClicked(stack: Stack) {//TODO go to todos fragment
        toast(stack.title)
    }

    fun stackEdit(stack: Stack): Boolean {//TODO edit stack name
        toast(stack.title)
        return false
    }

    fun createNewStack() {//TODO new stack
        toast("new stack")
     //   expandStackEdit()
//        val action = StacksFragmentDirections.actionStacksFragmentToStacksEditFragment()
//        findNavController().navigate(action)
    }


    fun expandStackEdit() {

     //   binding.topModal.doOnLayout {
            val va = ValueAnimator.ofInt(0,  binding.topModalContent.height );
         //   val va = ValueAnimator.ofInt(0,  300 * (Resources.getSystem().displayMetrics.density).toInt());
            va.apply {
                duration = 300
                addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    topModal.layoutParams.height = value
                    topModal.requestLayout()
                }
                doOnEnd {
                    toast((topModal.layoutParams.height / (Resources.getSystem().displayMetrics.density).toInt()).toString())
                    toast(topModal.layoutParams.height.toString())
                }
                start()
            }
  //  }


    }

    fun collapseStackEdit() {
        val va = ValueAnimator.ofInt(topModal.layoutParams.height, 0);
        va.apply {
            duration = 300
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                topModal.layoutParams.height = value
                topModal.requestLayout()
            }
            doOnEnd {
                toast((topModal.height / (Resources.getSystem().displayMetrics.density).toInt()).toString())
            }

            start()
        }

    }


}



