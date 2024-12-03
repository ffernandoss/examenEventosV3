package com.example.exameneventosv3

import org.locationtech.proj4j.CRSFactory
import org.locationtech.proj4j.CoordinateReferenceSystem
import org.locationtech.proj4j.CoordinateTransformFactory
import org.locationtech.proj4j.ProjCoordinate

object CoordinateConverter {
    private val crsFactory = CRSFactory()
    private val transformFactory = CoordinateTransformFactory()
    private val utmCRS: CoordinateReferenceSystem = crsFactory.createFromName("EPSG:32630") // UTM zone 30N
    private val geoCRS: CoordinateReferenceSystem = crsFactory.createFromName("EPSG:4326") // WGS84

    fun utmToLatLon(easting: Double, northing: Double): Pair<Double, Double> {
        val srcCoord = ProjCoordinate(easting, northing)
        val destCoord = ProjCoordinate()
        val transform = transformFactory.createTransform(utmCRS, geoCRS)
        transform.transform(srcCoord, destCoord)
        return Pair(destCoord.y, destCoord.x) // (latitude, longitude)
    }
}