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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataTanggal = arrayListOf<String>()
        val dataSubuh = arrayListOf<String>()
        val dataDzuhur = arrayListOf<String>()
        val dataAshar = arrayListOf<String>()
        val dataMagrib = arrayListOf<String>()
        val dataIsya = arrayListOf<String>()

        val recyclerView: RecyclerView = findViewById(R.id.rvSholat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        Config().getService().getModelWaktu().enqueue(object : Callback<JadwalModel> {

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