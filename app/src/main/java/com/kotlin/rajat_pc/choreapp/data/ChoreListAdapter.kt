package com.kotlin.rajat_pc.choreapp.data

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kotlin.rajat_pc.choreapp.R
import com.kotlin.rajat_pc.choreapp.activity.ChoreListActivity
import com.kotlin.rajat_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*


class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context):
        RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder{
        //create our view from our xml file
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context, list)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(list[position])
    }

    // make class inner to invoke certain function
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Chore>): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list

        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var assignBy = itemView.findViewById(R.id.listAssignBy) as TextView
        var assignDate = itemView.findViewById(R.id.listDate) as TextView
        var assignTo = itemView.findViewById(R.id.listAssignedTo) as TextView

        var deleteButton = itemView.findViewById(R.id.listDeleteButtom) as Button
        var editButton = itemView.findViewById(R.id.listEditButtom) as Button


        fun bindView(chore: Chore){
            choreName.text = chore.choreName
            assignBy.text = chore.assignedBy
            assignTo.text = chore.assignedTo
            assignDate.text = chore.showHumanDate(System.currentTimeMillis())
            deleteButton.setOnClickListener (this)
            editButton.setOnClickListener (this)
        }

        override fun onClick(p0: View?) {

            var mPositon: Int = adapterPosition
            var chore = mList[mPositon]



            when(p0!!.id) {

                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id -> {
                    editChore(chore)

                }

            }

        }


        fun deleteChore(id: Int){

            var db: ChoreDatabaseHandler = ChoreDatabaseHandler(mContext)
            db.deleteChore(id)

        }

        fun editChore(chore: Chore){

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: ChoreDatabaseHandler = ChoreDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var choreName = view.popEnterChore
            var assignedBy = view.popEnterAssignBy
            var assignedTo = view.popEnterAssignTo
            var saveButton = view.popSaveChore

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var name = choreName.text.toString().trim()
                var aBy = assignedBy.text.toString().trim()
                var aTo = assignedTo.text.toString().trim()
                if(!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(aBy)
                        && !TextUtils.isEmpty(aTo)){
                   // var chore = Chore()
                    chore.choreName = name
                    chore.assignedBy = aBy
                    chore.assignedTo = aTo

                    dbHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)


                    dialog!!.dismiss()

                }else{

                }
            }
        }

    }

}