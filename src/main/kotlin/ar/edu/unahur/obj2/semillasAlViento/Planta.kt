package ar.edu.unahur.obj2.semillasAlViento

abstract class Planta(val anioObtencionSemilla: Int, val altura: Double) {
  fun esFuerte() = this.horasDeSolQueTolera() > 10
  open fun daSemillas() = this.esFuerte()

  abstract fun horasDeSolQueTolera(): Int
}

class Menta(anioObtencionSemilla: Int, altura: Double) : Planta(anioObtencionSemilla, altura) {
  override fun horasDeSolQueTolera() = 6
  override fun daSemillas() = super.daSemillas() || altura > 0.4
}

open class Soja(anioObtencionSemilla: Int, altura: Double) : Planta(anioObtencionSemilla, altura) {
  override fun horasDeSolQueTolera(): Int  {
    // ¡Magia de Kotlin! El `when` es como un `if` pero más poderoso:
    // evalúa cada línea en orden y devuelve lo que está después de la flecha.
    val horasBase = when {
      altura < 0.5  -> 6
      altura < 1    -> 7
      else          -> 9
    }

    return horasBase
  }


  override fun daSemillas(): Boolean  {
    return super.daSemillas() || (this.anioObtencionSemilla > 2007 && this.altura > 1)
  }
}

class SojaTransgenica(anioObtencionSemilla: Int, altura: Double) :Soja(anioObtencionSemilla,altura) {
  override fun daSemillas() = false
  override fun horasDeSolQueTolera() = super.horasDeSolQueTolera() * 2
}
