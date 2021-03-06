package com.infinum.isa.playground.lecturethree.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.infinum.isa.playground.PlaygroundApp
import com.infinum.isa.playground.R
import com.infinum.isa.playground.databinding.ActivitySuperherosBinding
import com.infinum.isa.playground.databinding.DialogAddSuperheroBinding
import com.infinum.isa.playground.lecturefive.SuperheroesViewModel
import com.infinum.isa.playground.lectureseven.SuperheroViewModelFactory
import com.infinum.isa.playground.lecturethree.model.Superhero

class SuperherosActivity : AppCompatActivity() {

    companion object {

        fun buildIntent(context: Activity): Intent {
            return Intent(context, SuperherosActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySuperherosBinding

    private var superherosAdapter: SuperherosAdapter? = null

    private val viewModel: SuperheroesViewModel by viewModels {
        SuperheroViewModelFactory((application as PlaygroundApp).superheroesDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuperherosBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initSuperherosRecycler()

        viewModel.getAllSuperheroes().observe(this, { superheroes ->
//            updateItems(superheroes.map { Superhero(it.name, it.imageResourceId) })
        })

        initAddSuperheroButton()

        initSuperheroCardContent()

        binding.loadItemsButton.setOnClickListener {
            binding.superheroCard.animateTitle()
        }
    }

    private fun initSuperheroCardContent() {
        binding.superheroCard.setTitle("Hrvatko je pobjednik")
        binding.superheroCard.setImage(R.drawable.hrvatko)
    }

//    private fun initSuperherosRecycler() {
//        superherosAdapter = SuperherosAdapter(emptyList()) { item ->
//            Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
//        }
//
//        binding.superherosRecyclerView.layoutManager = LinearLayoutManager(this)
//        binding.superherosRecyclerView.adapter = superherosAdapter
//
//        binding.superherosRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//    }
//
//    private fun updateItems(superheros: List<Superhero>) {
//        superherosAdapter?.setItems(superheros)
//        binding.emptyStateLabel.isVisible = false
//        binding.superherosRecyclerView.isVisible = true
//    }

    private fun initAddSuperheroButton() {
        binding.addSuperheroButton.setOnClickListener {
            showAddSuperheroBottomSheet()
        }
    }

    private fun showAddSuperheroBottomSheet() {
        val dialog = BottomSheetDialog(this)

        val bottomSheetBinding = DialogAddSuperheroBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.confirmButton.setOnClickListener {
            addSuperheroToList(bottomSheetBinding.nameInput.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addSuperheroToList(name: String) {
        viewModel.addSuperhero(Superhero(name, R.drawable.ic_placeholder))
//        superherosAdapter?.addItem(Superhero(name, R.drawable.ic_placeholder))
    }
}