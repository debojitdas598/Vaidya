package com.example.vaidya.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.vaidya.TimestampAxisValueFormatter
import com.example.vaidya.databinding.FragmentHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment() {


    val timestampList = ArrayList<Long>()
    val valueList = ArrayList<Double>()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val queue = Volley.newRequestQueue(context)
        val url = "https://industrial.api.ubidots.com/api/v1.6/variables/65c7a2f0c7362f000d1485c6/values"
        fetch()
        fetchBodyTemp()
        fetchHeartRate()

        return binding.root


    }

    private fun fetchHeartRate() {
            val queue = Volley.newRequestQueue(context)
            val url = "https://industrial.api.ubidots.com/api/v1.6/variables/65c71e3bc87397000d8893ea/values"

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val results = response.getJSONArray("results")
                        // Get the latest result
                        val latestResult = results.getJSONObject(results.length() - 1)
                        val timestamp = latestResult.getLong("timestamp")
                        val value = latestResult.getDouble("value")
                        // Update the TextView with the latest value
                        binding.heartrate.text = value.toString()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    // Handle errors
                    error.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["X-Auth-Token"] = "BBUS-ZdDrvmbwHMtcEsf1SUyDv5BcpU2B2y"
                    return headers
                }
            }

            queue.add(jsonObjectRequest)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                fetchBodyTemp()
                fetchHeartRate()
                // Repeat after a delay (e.g., every 10 seconds)
                handler.postDelayed(this, 10000) // 10 seconds
            }
        }, 300)
    }

    private fun fetch() {
        val queue = Volley.newRequestQueue(context)
        val url = "https://industrial.api.ubidots.com/api/v1.6/variables/65c724cd8c114c000ebfd08b/values"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val result = results.getJSONObject(i)
                        val timestamp = result.getLong("timestamp")
                        timestampList.add(timestamp)
                        val value = result.getDouble("value")
                        valueList.add(value)
                        // Do something with the timestamp and value
                        Log.d("Data", "Timestamp: $timestamp, Value: $value")
                    }
                    graph(results.length())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors
                error.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Auth-Token"] = "BBUS-ZdDrvmbwHMtcEsf1SUyDv5BcpU2B2y"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
    private fun fetchBodyTemp() {
        val queue = Volley.newRequestQueue(context)
        val url = "https://industrial.api.ubidots.com/api/v1.6/variables/65c7a2f0c7362f000d1485c6/values"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val results = response.getJSONArray("results")
                    // Get the latest result
                    val latestResult = results.getJSONObject(results.length() - 1)
                    val timestamp = latestResult.getLong("timestamp")
                    val value = latestResult.getDouble("value")
                    // Update the TextView with the latest value
                    binding.bodytemp.text = value.toString()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors
                error.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Auth-Token"] = "BBUS-ZdDrvmbwHMtcEsf1SUyDv5BcpU2B2y"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
    private fun timestamper(timestamp:Long) : String{

        val date = Date(timestamp)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = format.format(date)
        return formattedTime
        }


    private fun graph(length:Int){
        var entries = ArrayList<Entry>()
        for (i in 0..50) {
            val x = i.toFloat()
            val y = valueList.get(i).toFloat();
            entries.add(Entry(x, y))
        }
        Log.d("TAG", "graph: "+entries)
            val dataSet = LineDataSet(entries, "Data")
            dataSet.color = Color.BLUE
            dataSet.setCircleColor(Color.BLUE)
            dataSet.lineWidth = 2f
            dataSet.valueTextColor = Color.BLACK

            val lineData = LineData(dataSet)

            binding.graph.data = lineData
            binding.graph.invalidate()

            // Customize x-axis
            val xAxis = binding.graph.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
             xAxis.valueFormatter = TimestampAxisValueFormatter()
             // Custom value formatter for timestamps

            // Customize y-axis
            val yAxis: YAxis = binding.graph.axisLeft
            yAxis.textColor = Color.BLACK

            // Hide right y-axis
            binding.graph.axisRight.isEnabled = false
        }

    }





