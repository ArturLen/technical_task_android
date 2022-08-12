package com.example.sliideusersbook.userslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sliideusersbook.R
import com.example.sliideusersbook.databinding.AddUserDialogLayoutBinding
import com.example.sliideusersbook.databinding.UsersListFragmentBinding
import com.example.sliideusersbook.userslist.model.User
import com.example.sliideusersbook.userslist.model.UserGender
import com.example.sliideusersbook.userslist.model.UserStatus
import com.example.sliideusersbook.userslist.ui.adapter.UsersListAdapter
import com.example.sliideusersbook.userslist.viewmodel.UsersListViewModel
import com.example.sliideusersbook.utils.EmailValidator.isEmailValid
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private lateinit var binding: UsersListFragmentBinding

    private val viewModel: UsersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = UsersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userListLiveData.observe(viewLifecycleOwner, Observer(this::onUiStateChanged))

        viewModel.loadUsers()

        binding.addUserButton.setOnClickListener {
            showAddUserDialog()
        }
    }

    private fun onUiStateChanged(state: UsersListUIState) {
        when (state) {
            is UsersListUIState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is UsersListUIState.Error -> {
                binding.progressBar.visibility = View.GONE
                showErrorMessage(state.type)
            }
            is UsersListUIState.UsersListLoaded -> {
                binding.progressBar.visibility = View.GONE
                binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.usersRecyclerView.adapter =
                    UsersListAdapter(state.users) { user -> showRemovalConfirmationDialog(user) }
                binding.usersRecyclerView.visibility = View.VISIBLE
            }
            is UsersListUIState.UsersDeleted -> {
                binding.progressBar.visibility = View.GONE
                showMessage(getString(R.string.users_list_deleting_success))
                viewModel.loadUsers()
            }
            is UsersListUIState.UsersAdded -> {
                binding.progressBar.visibility = View.GONE
                showMessage(getString(R.string.users_list_adding_success))
                viewModel.loadUsers()
            }
        }
    }

    private fun showRemovalConfirmationDialog(user: User) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.users_list_removal_dialog_confirmation_message, user.name))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> viewModel.deleteUser(user) }
            .setNegativeButton(getString(R.string.no)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .create()
            .show()
    }

    private fun showAddUserDialog() {
        val binding = AddUserDialogLayoutBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .setTitle(getString(R.string.add_user_dialog_title))
            .setPositiveButton(getString(R.string.add_user_dialog_add_button_title), null)
            .setNegativeButton(getString(R.string.add_user_dialog_cancel_button_title)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .create()
        dialog.show()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (isFormValid(binding)) {
                viewModel.addUser(
                    binding.addUserUsernameInputEditText.text.toString(),
                    binding.addUserUserEmailInputEditText.text.toString(),
                    if (binding.maleRadioButton.isChecked) UserGender.MALE else UserGender.FEMALE,
                    if (binding.addUserIsActiveSwitch.isChecked) UserStatus.ACTIVE else UserStatus.INACTIVE
                )
                dialog.dismiss()
            }
        }
    }

    private fun showErrorMessage(actionType: ErrorActionType) {
        when (actionType) {
            is ErrorActionType.Loading -> {
                showMessage(getString(R.string.users_list_loading_error))
            }
            is ErrorActionType.Adding -> {
                showMessage(getString(R.string.users_list_adding_error))
            }
            is ErrorActionType.Removing -> {
                showMessage(getString(R.string.users_list_removing_error))
            }
        }
    }

    private fun isFormValid(binding: AddUserDialogLayoutBinding): Boolean {
        val username = binding.addUserUsernameInputEditText.text.toString()
        val email = binding.addUserUserEmailInputEditText.text.toString()
        var isFormValid = true
        if (username.isNullOrEmpty()) {
            binding.addUserUsernameInputEditText.error = getString(R.string.add_user_empty_field_error_message)
            isFormValid = false
        }
        if (email.isNullOrBlank()) {
            binding.addUserUserEmailInputEditText.error = getString(R.string.add_user_empty_field_error_message)
            isFormValid = false
        } else {
            if (!isEmailValid(email)) {
                binding.addUserUserEmailInputEditText.error = getString(R.string.add_user_wrong_email_error_message)
                isFormValid = false
            }
        }
        return isFormValid
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}