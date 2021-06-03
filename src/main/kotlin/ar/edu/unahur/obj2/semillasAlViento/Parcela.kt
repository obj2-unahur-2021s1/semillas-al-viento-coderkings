package ar.edu.unahur.obj2.semillasAlViento

class Parcela(val ancho: Int, val largo: Int, val horasSolPorDia: Int) {
  val plantas = mutableListOf<Planta>()

  fun superficie() = ancho * largo
  fun cantidadMaximaPlantas() =
    if (ancho > largo) ancho * largo / 5 else ancho * largo / 3 + largo

  fun plantar(planta: Planta) {
    if (this.cantidadPlantas() == this.cantidadMaximaPlantas()) {
      throw Exception("Ya no hay lugar en esta parcela")
    } else if (horasSolPorDia > planta.horasDeSolQueTolera() + 2) {
      throw Exception("No se puede plantar esto acá, se va a quemar")
    } else {
      plantas.add(planta)
    }
  }

  fun tieneComplicaciones() =
    plantas.any { it.horasDeSolQueTolera() < horasSolPorDia }

  fun esSemillera() = plantas.all { planta -> planta.daSemillas()}
  fun cantidadPlantas() = plantas.size
}

class Agricultora(val parcelas: List<Parcela>) {

  fun parcelasSemilleras() =
    parcelas.filter {
      parcela -> parcela.esSemillera()
    }

  fun plantarEstrategicamente(planta: Planta) {
    this.parcelaElegida().plantar(planta)
  }

  fun parcelaElegida() =
    parcelas.maxByOrNull { it.cantidadMaximaPlantas() - it.cantidadPlantas() }!!
}
