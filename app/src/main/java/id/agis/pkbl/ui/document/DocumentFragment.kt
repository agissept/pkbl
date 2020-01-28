package id.agis.pkbl.ui.document

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.util.getFileModelsFromFiles
import id.agis.pkbl.util.getFilesFromPath
import kotlinx.android.synthetic.main.fragment_document.*
import id.agis.pkbl.util.FileType
import id.agis.pkbl.util.launchFileIntent
import id.agis.pkbl.ui.detail.FileOptionsDialog
import org.jetbrains.anko.support.v4.toast
import id.agis.pkbl.util.FileUtil.deleteFile as FileUtilsDeleteFile


class DocumentFragment : Fragment() {

    private lateinit var mFilesAdapter: FilesRecyclerAdapter
    private lateinit var PATH: String
    private lateinit var mCallback: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(fileModel: FileModel)

        fun onLongClick(fileModel: FileModel)
    }

    companion object {
        private const val ARG_PATH: String = "com.example.fileexplore.fileslist.path"
        private const val OPTIONS_DIALOG_TAG: String = "id.agis.pkbl.main.options_dialog"

        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path: String = ""

        fun build(): DocumentFragment {
            val fragment = DocumentFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filePath = arguments?.getString(ARG_PATH)
        if (filePath == null) {
            Toast.makeText(context, "Path should not be null!", Toast.LENGTH_SHORT).show()
            return
        }
        PATH = "storage/emulated/0/pkbl"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        PATH = "storage/emulated/0/pkbl"

        filesRecyclerView.layoutManager = LinearLayoutManager(context)
        mFilesAdapter = FilesRecyclerAdapter()
        filesRecyclerView.adapter = mFilesAdapter

        mFilesAdapter.onItemClickListener = {
            if (it.fileType == FileType.FOLDER) {
                    val action = DocumentFragmentDirections.actionNavDocumentToNavDocumentFolder2("$PATH/${it.name}")
                    findNavController().navigate(action)
            } else {
                context!!.launchFileIntent(it)
            }
        }

        mFilesAdapter.onItemLongClickListener = {
            val optionsDialog = FileOptionsDialog.build {}

            optionsDialog.onDeleteClickListener = {
                FileUtilsDeleteFile(it.path)
                updateDate()
                toast("'${it.name}' deleted successfully.")
            }


            optionsDialog.show(childFragmentManager, OPTIONS_DIALOG_TAG)
        }

        updateDate()
    }

    private fun updateDate() {
        val files = getFileModelsFromFiles(getFilesFromPath(PATH))

        if (files.isEmpty()) {
            emptyFolderLayout.visibility = View.VISIBLE
        } else {
            emptyFolderLayout.visibility = View.INVISIBLE
        }

        mFilesAdapter.updateData(files)
    }
}