package learn.bymyself.sholatreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import learn.bymyself.sholatreminder.Network.Config
import learn.bymyself.sholatreminder.Network.JadwalModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.tan

class MainActivity : AppCompatActivity() {

    private var dataSubuh = mutableListOf<String>()
    private var dataDzuhur = mutableListOf<String>()
    private var dataAshar = mutableListOf<String>()
    private var dataMagrib = mutableListOf<String>()
    private var dataIsya = mutableListOf<String>()
    private var dataWaktu = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvWaktu: RecyclerView = findViewById(R.id.tanggal)
        val rvSubuh: RecyclerView = findViewById(R.id.subuh)
        val rvDzuhur: RecyclerView = findViewById(R.id.dzuhur)
        val rvAshar: RecyclerView = findViewById(R.id.ashar)
        val rvMaghrib: RecyclerView = findViewById(R.id.maghrib)
        val rvIsya: RecyclerView = findViewById(R.id.isya)

        Config().getService().getModelWaktu().enqueue(object : Callback<JadwalModel>{
            override fun onResponse(
                call: Call<JadwalModel>,
                response: Response<JadwalModel>
            ) {
                val hasil = response.body()!!
                val hasil2 = hasil.results?.datetime
                if (hasil2 != null) {
                    for (i in hasil2){
                        dataSubuh.add(i?.times?.fajr.toString())
                        dataDzuhur.add(i?.times?.dhuhr.toString())
                        dataAshar.add(i?.times?.asr.toString())
                        dataMagrib.add(i?.times?.maghrib.toString())
                        dataIsya.add(i?.times?.isha.toString())
                        dataWaktu.add(i?.date?.gregorian.toString())

                        rvSubuh.adapter = Adapter(dataSubuh)
                        rvDzuhur.adapter = Adapter(dataDzuhur)
                        rvAshar.adapter = Adapter(dataAshar)
                        rvMaghrib.adapter = Adapter(dataMagrib)
                        rvIsya.adapter = Adapter(dataIsya)
                        rvWaktu.adapter = Adapter(dataWaktu)

                        rvSubuh.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvDzuhur.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvAshar.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvMaghrib.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvIsya.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvWaktu.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }

            override fun onFailure(call: Call<JadwalModel>, t: Throwable) {
                val announce = "Masalah koneksi, 403"
                Toast.makeText(this@MainActivity, announce, Toast.LENGTH_LONG).show()
            }

        })

    }
}