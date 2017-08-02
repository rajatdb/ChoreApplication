package com.kotlin.rajat_pc.choreapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kotlin.rajat_pc.choreapp.R
import com.kotlin.rajat_pc.choreapp.data.ChoreDatabaseHandler
import com.kotlin.rajat_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbHandler: ChoreDatabaseHandler? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        dbHandler = ChoreDatabaseHandler(this)

        checkDB()

        saveChore.setOnClickListener {
            progressDialog!!.setMessage("Saving....")
            progressDialog!!.show()

            if(!TextUtils.isEmpty(popEnterChore.text.toString())
                    && !TextUtils.isEmpty(popEnterAssignBy.text.toString())
                    && !TextUtils.isEmpty(popEnterAssignTo.text.toString())){

                //save to database
                var chore = Chore()
                chore.choreName = popEnterChore.text.toString()
                chore.assignedBy = popEnterAssignBy.text.toString()
                chore.assignedTo = popEnterAssignTo.text.toString()

                saveToDB(chore)
                //progressDialog!!.cancel()
                startActivity(Intent(this, ChoreListActivity::class.java))

            } else{
                Toast.makeText(this, "Please enter chore", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkDB(){
        if(dbHandler!!.getChoresCount() > 0){
            startActivity(Intent(this, ChoreListActivity::class.java))
        }
    }

    fun saveToDB(chore: Chore){
        dbHandler!!.createChore(chore)

    }
}
