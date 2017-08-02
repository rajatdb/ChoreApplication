package com.kotlin.rajat_pc.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.kotlin.rajat_pc.choreapp.R
import com.kotlin.rajat_pc.choreapp.data.ChoreDatabaseHandler
import com.kotlin.rajat_pc.choreapp.data.ChoreListAdapter
import com.kotlin.rajat_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*


class ChoreListActivity : AppCompatActivity() {

    var dbHandler: ChoreDatabaseHandler? = null
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)


        dbHandler = ChoreDatabaseHandler(this)
        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!,this)


        //setup list = recyclerView
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter


        //Load our Chores
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()


        for(c in choreList!!.iterator()){

            val chore = Chore()
            chore.choreName = "Chore: ${c.choreName}"
            chore.assignedBy = "Assigned By: ${c.assignedBy}"
            chore.assignedTo = "Assigned To: ${c.assignedTo}"
            chore.id = c.id
            chore.showHumanDate(c.timeAssigned!!)


            Log.d("====ID====", c.id.toString())
            Log.d("====Name====", c.choreName)
            Log.d("====Date====", chore.showHumanDate(c.timeAssigned!!))
            Log.d("====aTo====", c.assignedTo)
            Log.d("====aBy====", c.assignedBy)

            choreListItems!!.add(chore)

        }
        adapter!!.notifyDataSetChanged()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == R.id.add_menu_button){
            Log.d("Item Clicked", "Item Clicked")
            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)
    }


    fun  createPopupDialog(){

        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChore
        var assignedBy = view.popEnterAssignBy
        var assignedTo = view.popEnterAssignTo
        var saveButton = view.popSaveChore

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var name = choreName.text.toString().trim()
            var aBy = assignedBy.text.toString().trim()
            var aTo = assignedTo.text.toString().trim()
            if(!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(aBy)
                    && !TextUtils.isEmpty(aTo)){
                var chore = Chore()
                chore.choreName = name
                chore.assignedBy = aBy
                chore.assignedTo = aTo

                dbHandler!!.createChore(chore)
                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()

            }else{


            }
        }

    }
}
