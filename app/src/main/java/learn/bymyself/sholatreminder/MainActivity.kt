package learn.bymyself.sholatreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import learn.bymyself.sholatreminder.Network.Config
import learn.bymyself.sholatreminder.Network.JadwalModel
import learn.bymyself.sholatreminder.Network.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.tan

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)

        val dataTanggal = arrayListOf<String>()
        val dataSubuh = arrayListOf<String>()
        val dataDzuhur = arrayListOf<String>()
        val dataAshar = arrayListOf<String>()
        val dataMagrib = arrayListOf<String>()
        val dataIsya = arrayListOf<String>()

        //spinner
        val spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf("Jakarta", "Bandung", "Surabaya"))
        spinner.adapter = spinnerAdapter

        //rv
        val recyclerView: RecyclerView = findViewById(R.id.rvSholat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val lokasi = mapOf<String, Any>(
            "Jakarta" to mapOf<String, String>(
                "lng" to "107.1485", "lan" to "-6.2474", "ev" to "8", "mo" to "2021-0"
            ),
            "Denpasar" to mapOf<String, String>(
                "lng" to "115.2126", "lan" to "-8.6705", "ev" to "8", "mo" to "2021-0"
            )
        )

        for (i in lokasi.keys) {

        }

        Config().getService().getModelWaktu("106.816666", "-6.200000", "8", "2021-10").enqueue(object : Callback<JadwalModel> {

            override fun onResponse(call: Call<JadwalModel>, response: Response<JadwalModel>) {
                val panggil1 = response.body()
                val panggil2 = panggil1?.results?.datetime

                for (list in panggil2!!.indices){
                    val waktuSholat = panggil2[list]?.times
                    val tanggal = panggil2[list]?.date

                    dataTanggal.add(tanggal?.gregorian.toString())
                    dataSubuh.add(waktuSholat?.fajr.toString())
                    dataDzuhur.add(waktuSholat?.dhuhr.toString())
                    dataAshar.add(waktuSholat?.asr.toString())
                    dataMagrib.add(waktuSholat?.maghrib.toString())
                    dataIsya.add(waktuSholat?.isha.toString())

                    recyclerView.adapter = Adapter(dataTanggal, dataSubuh, dataDzuhur, dataAshar, dataMagrib, dataIsya)
                }
            }

            override fun onFailure(call: Call<JadwalModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "$t", Toast.LENGTH_SHORT ).show()
            }

        })
    }

}