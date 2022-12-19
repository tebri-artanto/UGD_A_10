package com.example.testugd1

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testugd1.databinding.FragmentProfileBinding
import com.example.testugd1.databinding.FragmentSearchBinding
import com.example.testugd1.tidakDipakai.MainProgressBar
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.io.IOException
import java.nio.charset.StandardCharsets

class searchFragment : Fragment() {
    var modelMainList: MutableList<ModelMain> = ArrayList()
    lateinit var mapController: MapController
    lateinit var overlayItem: ArrayList<OverlayItem>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMap.setOnClickListener{
            val moveEdit = Intent(activity, MapMenu::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }
        binding.btnCamera.setOnClickListener{
            val moveEdit = Intent(activity, CameraMenu::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }

        binding.btnDestinasi.setOnClickListener{
            val moveEdit = Intent(activity, DestinasiMain::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }

        binding.btnQrCode.setOnClickListener{
            val moveEdit = Intent(activity, QRMain::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }

        binding.btnRekomendasi.setOnClickListener{
            val moveEdit = Intent(activity, MainProgressBar::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }

        binding.btnBeliTiket.setOnClickListener{
            val moveEdit = Intent(activity, TiketMain::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }
    }


}