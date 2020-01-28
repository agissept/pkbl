package id.agis.pkbl.ui.documentfolder


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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


class DocumentFolderFragment : Fragment() {

    private lateinit var adapter: FilesRecyclerAdapter
    private lateinit var PATH: String
    private val listFile = mutableListOf<FileModel>()

    companion object {
        private const val ARG_PATH: String = "com.example.fileexplore.fileslist.path"
        private const val OPTIONS_DIALOG_TAG: String = "id.agis.pkbl.main.options_dialog"
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
         arguments?.let {
            PATH = DocumentFolderFragmentArgs.fromBundle(it).path
        }


        filesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FilesRecyclerAdapter(listFile)
        filesRecyclerView.adapter = adapter

        adapter.onItemClickListener = {
            if (it.fileType == FileType.FOLDER) {
                val action =
                    DocumentFolderFragmentDirections.actionNavDocumentFolderSelf("$PATH/${it.name}")
                findNavController().navigate(action)
            } else {
                context!!.launchFileIntent(it)
            }
        }

        adapter.onItemLongClickListener = {
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
        listFile.clear()
        listFile.addAll(getFileModelsFromFiles(getFilesFromPath(PATH)))

        if (listFile.isEmpty()) {
            emptyFolderLayout.visibility = View.VISIBLE
        } else {
            emptyFolderLayout.visibility = View.INVISIBLE
        }

        adapter.notifyDataSetChanged()
    }
}