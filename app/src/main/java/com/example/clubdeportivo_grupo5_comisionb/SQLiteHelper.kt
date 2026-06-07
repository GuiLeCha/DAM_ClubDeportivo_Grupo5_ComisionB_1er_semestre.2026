package com.example.clubdeportivo_grupo5_comisionb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SQLiteHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "clubdeportivo.db"
        private const val DATABASE_VERSION = 2

        private const val TABLA_SOCIOS = "socios"
        private const val TABLA_VISITANTES = "visitantes"
        private const val TABLA_PAGOS = "pagos"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val crearTablaSocios = """
            CREATE TABLE $TABLA_SOCIOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                dni TEXT NOT NULL UNIQUE,
                telefono TEXT,
                email TEXT,
                direccion TEXT,
                fechaNacimiento TEXT,
                aptoFisico INTEGER NOT NULL,
                carnetEntregado INTEGER NOT NULL,
                fechaAlta TEXT NOT NULL,
                fechaVencimientoCuota TEXT NOT NULL,
                activo INTEGER NOT NULL DEFAULT 1
            )
        """.trimIndent()

        val crearTablaVisitantes = """
            CREATE TABLE $TABLA_VISITANTES (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                dni TEXT NOT NULL UNIQUE,
                telefono TEXT,
                email TEXT,
                actividad TEXT NOT NULL,
                aptoFisico INTEGER NOT NULL,
                fechaRegistro TEXT NOT NULL,
                activo INTEGER NOT NULL DEFAULT 1
            )
        """.trimIndent()

        val crearTablaPagos = """
            CREATE TABLE $TABLA_PAGOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipoCliente TEXT NOT NULL,
                clienteId INTEGER NOT NULL,
                nombreCliente TEXT NOT NULL,
                dniCliente TEXT NOT NULL,
                concepto TEXT NOT NULL,
                monto REAL NOT NULL,
                medioPago TEXT NOT NULL,
                fechaPago TEXT NOT NULL,
                fechaVencimientoGenerada TEXT
            )
        """.trimIndent()

        db.execSQL(crearTablaSocios)
        db.execSQL(crearTablaVisitantes)
        db.execSQL(crearTablaPagos)

        insertarDatosIniciales(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PAGOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_VISITANTES")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_SOCIOS")
        onCreate(db)
    }

    fun obtenerFechaHoy(): String {
        return obtenerFechaConDiferenciaDias(0)
    }

    fun obtenerFechaConDiferenciaDias(dias: Int): String {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendario = Calendar.getInstance()
        calendario.add(Calendar.DAY_OF_YEAR, dias)
        return formato.format(calendario.time)
    }

    fun obtenerFechaConDiferenciaMeses(meses: Int): String {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendario = Calendar.getInstance()
        calendario.add(Calendar.MONTH, meses)
        return formato.format(calendario.time)
    }

    fun calcularNuevoVencimientoSocio(socioId: Int): String {
        val socio = obtenerSocioPorId(socioId)
        val fechaHoy = obtenerFechaHoy()

        if (socio == null) {
            return obtenerFechaConDiferenciaMeses(1)
        }

        val fechaVencimientoActual = socio.fechaVencimientoCuota

        return if (fechaVencimientoActual >= fechaHoy) {
            sumarMesesAFecha(fechaVencimientoActual, 1)
        } else {
            obtenerFechaConDiferenciaMeses(1)
        }
    }

    private fun sumarMesesAFecha(fecha: String, meses: Int): String {
        return try {
            val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendario = Calendar.getInstance()
            val fechaParseada = formato.parse(fecha)

            if (fechaParseada != null) {
                calendario.time = fechaParseada
            }

            calendario.add(Calendar.MONTH, meses)
            formato.format(calendario.time)
        } catch (e: Exception) {
            obtenerFechaConDiferenciaMeses(meses)
        }
    }

    private fun insertarDatosIniciales(db: SQLiteDatabase) {
        val idGuillermo = insertarSocioEnDb(
            db,
            Socio(
                nombre = "Guillermo",
                apellido = "Chacon",
                dni = "30111222",
                telefono = "1122334455",
                email = "guillermo.chacon@email.com",
                direccion = "Av. Siempre Viva 123",
                fechaNacimiento = "1987-04-15",
                aptoFisico = true,
                carnetEntregado = true,
                fechaAlta = obtenerFechaConDiferenciaDias(-30),
                fechaVencimientoCuota = obtenerFechaConDiferenciaDias(10),
                activo = true
            )
        ).toInt()

        val idJulia = insertarSocioEnDb(
            db,
            Socio(
                nombre = "Julia",
                apellido = "Piassentini",
                dni = "23721999",
                telefono = "1166677788",
                email = "julia.piassentini@email.com",
                direccion = "San Martín 450",
                fechaNacimiento = "1992-09-21",
                aptoFisico = true,
                carnetEntregado = true,
                fechaAlta = obtenerFechaConDiferenciaDias(-30),
                fechaVencimientoCuota = obtenerFechaHoy(),
                activo = true
            )
        ).toInt()

        val idMariana = insertarSocioEnDb(
            db,
            Socio(
                nombre = "Mariana",
                apellido = "Borda",
                dni = "28444555",
                telefono = "1144455566",
                email = "mariana.borda@email.com",
                direccion = "Belgrano 875",
                fechaNacimiento = "1990-12-03",
                aptoFisico = true,
                carnetEntregado = false,
                fechaAlta = obtenerFechaConDiferenciaDias(-30),
                fechaVencimientoCuota = obtenerFechaHoy(),
                activo = true
            )
        ).toInt()

        val idFernando = insertarVisitanteEnDb(
            db,
            Visitante(
                nombre = "Fernando",
                apellido = "Baca",
                dni = "33444555",
                telefono = "1199988877",
                email = "fernando.baca@email.com",
                actividad = "Musculación",
                aptoFisico = true,
                fechaRegistro = obtenerFechaHoy(),
                activo = true
            )
        ).toInt()

        insertarVisitanteEnDb(
            db,
            Visitante(
                nombre = "Eric",
                apellido = "Bermudez",
                dni = "31222333",
                telefono = "1133322211",
                email = "eric.bermudez@email.com",
                actividad = "Pileta libre",
                aptoFisico = true,
                fechaRegistro = obtenerFechaHoy(),
                activo = true
            )
        )

        insertarPagoEnDb(
            db,
            Pago(
                tipoCliente = "SOCIO",
                clienteId = idGuillermo,
                nombreCliente = "Guillermo Chacon",
                dniCliente = "30111222",
                concepto = "Cuota mensual",
                monto = 12000.0,
                medioPago = "Efectivo",
                fechaPago = obtenerFechaConDiferenciaDias(-20),
                fechaVencimientoGenerada = obtenerFechaConDiferenciaDias(10)
            )
        )

        insertarPagoEnDb(
            db,
            Pago(
                tipoCliente = "SOCIO",
                clienteId = idJulia,
                nombreCliente = "Julia Piassentini",
                dniCliente = "23721999",
                concepto = "Cuota mensual",
                monto = 12000.0,
                medioPago = "Tarjeta",
                fechaPago = obtenerFechaConDiferenciaDias(-30),
                fechaVencimientoGenerada = obtenerFechaHoy()
            )
        )

        insertarPagoEnDb(
            db,
            Pago(
                tipoCliente = "SOCIO",
                clienteId = idMariana,
                nombreCliente = "Mariana Borda",
                dniCliente = "28444555",
                concepto = "Cuota mensual",
                monto = 12000.0,
                medioPago = "Transferencia",
                fechaPago = obtenerFechaConDiferenciaDias(-30),
                fechaVencimientoGenerada = obtenerFechaHoy()
            )
        )

        insertarPagoEnDb(
            db,
            Pago(
                tipoCliente = "VISITANTE",
                clienteId = idFernando,
                nombreCliente = "Fernando Baca",
                dniCliente = "33444555",
                concepto = "Actividad diaria",
                monto = 3500.0,
                medioPago = "Efectivo",
                fechaPago = obtenerFechaHoy(),
                fechaVencimientoGenerada = ""
            )
        )
    }

    fun insertarSocio(socio: Socio): Long {
        val db = writableDatabase
        val resultado = insertarSocioEnDb(db, socio)
        db.close()
        return resultado
    }

    private fun insertarSocioEnDb(db: SQLiteDatabase, socio: Socio): Long {
        val valores = ContentValues().apply {
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("dni", socio.dni)
            put("telefono", socio.telefono)
            put("email", socio.email)
            put("direccion", socio.direccion)
            put("fechaNacimiento", socio.fechaNacimiento)
            put("aptoFisico", if (socio.aptoFisico) 1 else 0)
            put("carnetEntregado", if (socio.carnetEntregado) 1 else 0)
            put("fechaAlta", socio.fechaAlta)
            put("fechaVencimientoCuota", socio.fechaVencimientoCuota)
            put("activo", if (socio.activo) 1 else 0)
        }

        return db.insert(TABLA_SOCIOS, null, valores)
    }

    fun insertarVisitante(visitante: Visitante): Long {
        val db = writableDatabase
        val resultado = insertarVisitanteEnDb(db, visitante)
        db.close()
        return resultado
    }

    private fun insertarVisitanteEnDb(db: SQLiteDatabase, visitante: Visitante): Long {
        val valores = ContentValues().apply {
            put("nombre", visitante.nombre)
            put("apellido", visitante.apellido)
            put("dni", visitante.dni)
            put("telefono", visitante.telefono)
            put("email", visitante.email)
            put("actividad", visitante.actividad)
            put("aptoFisico", if (visitante.aptoFisico) 1 else 0)
            put("fechaRegistro", visitante.fechaRegistro)
            put("activo", if (visitante.activo) 1 else 0)
        }

        return db.insert(TABLA_VISITANTES, null, valores)
    }

    fun insertarPago(pago: Pago): Long {
        val db = writableDatabase
        val resultado = insertarPagoEnDb(db, pago)
        db.close()
        return resultado
    }

    private fun insertarPagoEnDb(db: SQLiteDatabase, pago: Pago): Long {
        val valores = ContentValues().apply {
            put("tipoCliente", pago.tipoCliente)
            put("clienteId", pago.clienteId)
            put("nombreCliente", pago.nombreCliente)
            put("dniCliente", pago.dniCliente)
            put("concepto", pago.concepto)
            put("monto", pago.monto)
            put("medioPago", pago.medioPago)
            put("fechaPago", pago.fechaPago)
            put("fechaVencimientoGenerada", pago.fechaVencimientoGenerada)
        }

        return db.insert(TABLA_PAGOS, null, valores)
    }

    fun existeDniSocio(dni: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLA_SOCIOS WHERE dni = ?",
            arrayOf(dni)
        )

        var existe = false

        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0
        }

        cursor.close()
        db.close()
        return existe
    }

    fun existeDniVisitante(dni: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLA_VISITANTES WHERE dni = ?",
            arrayOf(dni)
        )

        var existe = false

        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0
        }

        cursor.close()
        db.close()
        return existe
    }

    fun obtenerSocios(): MutableList<Socio> {
        val listaSocios = mutableListOf<Socio>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_SOCIOS WHERE activo = 1 ORDER BY apellido, nombre",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                listaSocios.add(cursorASocio(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaSocios
    }

    fun buscarSocios(textoBusqueda: String): MutableList<Socio> {
        val listaSocios = mutableListOf<Socio>()
        val db = readableDatabase
        val texto = "%${textoBusqueda.trim()}%"

        val cursor = db.rawQuery(
            """
            SELECT *
            FROM $TABLA_SOCIOS
            WHERE activo = 1
              AND (
                    nombre LIKE ?
                 OR apellido LIKE ?
                 OR dni LIKE ?
              )
            ORDER BY apellido, nombre
            """.trimIndent(),
            arrayOf(texto, texto, texto)
        )

        if (cursor.moveToFirst()) {
            do {
                listaSocios.add(cursorASocio(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaSocios
    }

    fun obtenerVisitantes(): MutableList<Visitante> {
        val listaVisitantes = mutableListOf<Visitante>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_VISITANTES WHERE activo = 1 ORDER BY apellido, nombre",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                listaVisitantes.add(cursorAVisitante(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaVisitantes
    }

    fun buscarVisitantes(textoBusqueda: String): MutableList<Visitante> {
        val listaVisitantes = mutableListOf<Visitante>()
        val db = readableDatabase
        val texto = "%${textoBusqueda.trim()}%"

        val cursor = db.rawQuery(
            """
            SELECT *
            FROM $TABLA_VISITANTES
            WHERE activo = 1
              AND (
                    nombre LIKE ?
                 OR apellido LIKE ?
                 OR dni LIKE ?
                 OR actividad LIKE ?
              )
            ORDER BY apellido, nombre
            """.trimIndent(),
            arrayOf(texto, texto, texto, texto)
        )

        if (cursor.moveToFirst()) {
            do {
                listaVisitantes.add(cursorAVisitante(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaVisitantes
    }

    fun obtenerSocioPorId(id: Int): Socio? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_SOCIOS WHERE id = ?",
            arrayOf(id.toString())
        )

        var socio: Socio? = null

        if (cursor.moveToFirst()) {
            socio = cursorASocio(cursor)
        }

        cursor.close()
        db.close()
        return socio
    }

    fun obtenerVisitantePorId(id: Int): Visitante? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_VISITANTES WHERE id = ?",
            arrayOf(id.toString())
        )

        var visitante: Visitante? = null

        if (cursor.moveToFirst()) {
            visitante = cursorAVisitante(cursor)
        }

        cursor.close()
        db.close()
        return visitante
    }

    fun obtenerSocioPorDni(dni: String): Socio? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_SOCIOS WHERE dni = ?",
            arrayOf(dni)
        )

        var socio: Socio? = null

        if (cursor.moveToFirst()) {
            socio = cursorASocio(cursor)
        }

        cursor.close()
        db.close()
        return socio
    }

    fun obtenerVisitantePorDni(dni: String): Visitante? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_VISITANTES WHERE dni = ?",
            arrayOf(dni)
        )

        var visitante: Visitante? = null

        if (cursor.moveToFirst()) {
            visitante = cursorAVisitante(cursor)
        }

        cursor.close()
        db.close()
        return visitante
    }

    fun actualizarVencimientoSocioPorId(
        socioId: Int,
        nuevaFechaVencimiento: String,
        carnetEntregado: Boolean
    ): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("fechaVencimientoCuota", nuevaFechaVencimiento)
            put("carnetEntregado", if (carnetEntregado) 1 else 0)
        }

        val resultado = db.update(
            TABLA_SOCIOS,
            valores,
            "id = ?",
            arrayOf(socioId.toString())
        )

        db.close()
        return resultado
    }

    fun actualizarVencimientoSocioPorDni(
        dni: String,
        nuevaFechaVencimiento: String,
        carnetEntregado: Boolean
    ): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("fechaVencimientoCuota", nuevaFechaVencimiento)
            put("carnetEntregado", if (carnetEntregado) 1 else 0)
        }

        val resultado = db.update(
            TABLA_SOCIOS,
            valores,
            "dni = ?",
            arrayOf(dni)
        )

        db.close()
        return resultado
    }

    fun darDeBajaSocio(id: Int): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("activo", 0)
        }

        val resultado = db.update(
            TABLA_SOCIOS,
            valores,
            "id = ?",
            arrayOf(id.toString())
        )

        db.close()
        return resultado
    }

    fun darDeBajaVisitante(id: Int): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("activo", 0)
        }

        val resultado = db.update(
            TABLA_VISITANTES,
            valores,
            "id = ?",
            arrayOf(id.toString())
        )

        db.close()
        return resultado
    }

    fun obtenerVencimientosDelDia(fecha: String = obtenerFechaHoy()): MutableList<Socio> {
        val listaSocios = mutableListOf<Socio>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_SOCIOS WHERE activo = 1 AND fechaVencimientoCuota = ? ORDER BY apellido, nombre",
            arrayOf(fecha)
        )

        if (cursor.moveToFirst()) {
            do {
                listaSocios.add(cursorASocio(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaSocios
    }

    fun obtenerPagos(): MutableList<Pago> {
        val listaPagos = mutableListOf<Pago>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_PAGOS ORDER BY id DESC",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                listaPagos.add(cursorAPago(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPagos
    }

    fun obtenerUltimoPagoPorDni(dni: String): Pago? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLA_PAGOS WHERE dniCliente = ? ORDER BY id DESC LIMIT 1",
            arrayOf(dni)
        )

        var pago: Pago? = null

        if (cursor.moveToFirst()) {
            pago = cursorAPago(cursor)
        }

        cursor.close()
        db.close()
        return pago
    }

    private fun cursorASocio(cursor: Cursor): Socio {
        return Socio(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
            aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("aptoFisico")) == 1,
            carnetEntregado = cursor.getInt(cursor.getColumnIndexOrThrow("carnetEntregado")) == 1,
            fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fechaAlta")),
            fechaVencimientoCuota = cursor.getString(cursor.getColumnIndexOrThrow("fechaVencimientoCuota")),
            activo = cursor.getInt(cursor.getColumnIndexOrThrow("activo")) == 1
        )
    }

    private fun cursorAVisitante(cursor: Cursor): Visitante {
        return Visitante(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            actividad = cursor.getString(cursor.getColumnIndexOrThrow("actividad")),
            aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("aptoFisico")) == 1,
            fechaRegistro = cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistro")),
            activo = cursor.getInt(cursor.getColumnIndexOrThrow("activo")) == 1
        )
    }

    private fun cursorAPago(cursor: Cursor): Pago {
        return Pago(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            tipoCliente = cursor.getString(cursor.getColumnIndexOrThrow("tipoCliente")),
            clienteId = cursor.getInt(cursor.getColumnIndexOrThrow("clienteId")),
            nombreCliente = cursor.getString(cursor.getColumnIndexOrThrow("nombreCliente")),
            dniCliente = cursor.getString(cursor.getColumnIndexOrThrow("dniCliente")),
            concepto = cursor.getString(cursor.getColumnIndexOrThrow("concepto")),
            monto = cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
            medioPago = cursor.getString(cursor.getColumnIndexOrThrow("medioPago")),
            fechaPago = cursor.getString(cursor.getColumnIndexOrThrow("fechaPago")),
            fechaVencimientoGenerada = cursor.getString(cursor.getColumnIndexOrThrow("fechaVencimientoGenerada"))
        )
    }
}