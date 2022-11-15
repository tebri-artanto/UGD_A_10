package com.example.testugd1.Entity

class TempatPariwisata (var Nama_Tempat: String, var Kapasitas: Int) {
    companion object{
        var litsOfKelas = arrayOf(
            TempatPariwisata("Embung Nglanggeran", 1000),
            TempatPariwisata("Museum Gunungapi Merapi", 500),
            TempatPariwisata("Heha Sky View", 2000),
            TempatPariwisata("Gembira Loka Zoo", 2500),
            TempatPariwisata("Air Terjun Kedung Kandang", 1000),
            TempatPariwisata("Puncak Becici", 2000),
            TempatPariwisata("Gerbang Banyu Langit", 500),
            TempatPariwisata("Jogja Bay", 1000),
            TempatPariwisata("Mangrove Jembatan Api", 1000),
            TempatPariwisata("Pinus Asri", 2000)
        )
    }
}