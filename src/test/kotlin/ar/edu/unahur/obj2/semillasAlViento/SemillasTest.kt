package ar.edu.unahur.obj2.semillasAlViento

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class SemillasTest : DescribeSpec({
    describe("Testeo de los tipos de plantas y sus funciones") {
        describe("Menta") {
            val menta = Menta(2010, 1.7)
            val mentaNoDaSemillas = Menta(2008, 0.2)
            it("Horas de sol que tolera por dia = 6hs") {
                menta.horasDeSolQueTolera().shouldBe(6)
            }
            it("No es fuerte, ya que no tolera al sol") {
                menta.esFuerte().shouldBeFalse()
            }
            it("Da semillas, ya que comple la condicion de altura") {
                menta.daSemillas().shouldBeTrue()
            }
            it("No da Semillas, por que no cumple las 2 condiciones") {
                mentaNoDaSemillas.daSemillas().shouldBeFalse()
            }
        }
        describe("Soja") {
            val soja = Soja(2008, 0.2)
            val sojaConMasAltura = Soja(2007, 0.7)
            val sojaConMasDe1Metro = Soja(2009, 1.5)

            val sojaConObtencionReciente = Soja(2009, 2.2)
            val sojaConObtencionViejo = Soja(2002, 1.8)
            val sojaPequenia = Soja(2012, 0.6)
            val sojaViejoYchico = Soja(1996, 0.2)

            it("Horas de sol que tolera, depende de su altura. opcion 1. 6hs") {
                soja.horasDeSolQueTolera().shouldBe(6)
            }
            it("Horas de sol que tolera, depende de su altura opcion 2. 7hs") {
                sojaConMasAltura.horasDeSolQueTolera().shouldBe(7)
            }
            it("Horas de sol que tolera, depende de su altura opcion 3. 9hs") {
                sojaConMasDe1Metro.horasDeSolQueTolera().shouldBe(9)
            }
            it("Da semillas") {
                sojaConObtencionReciente.daSemillas().shouldBeTrue()
            }
            it("No da semmillas, por que, su anio de obtencion, es viejo") {
                sojaConObtencionViejo.daSemillas().shouldBeFalse()
            }
            it("No da semillas, por que, su altura es pequeño") {
                sojaPequenia.daSemillas().shouldBeFalse()
            }
            it("No da semillas por que no cumple ningunda de las condiciones") {
                sojaViejoYchico.daSemillas().shouldBeFalse()
            }
        }
        describe("Soja Trasgenica") {
            val sojaTrasgenica = SojaTransgenica(2010, 0.4)
            it("Horas de sol que tolera, tolera el doble de hs, que una soja comun") {
                sojaTrasgenica.horasDeSolQueTolera().shouldBe(12)
            }
            it("Nunca da semillas") {
                sojaTrasgenica.daSemillas().shouldBeFalse()
            }
        }
    }
    describe("Testeo de Parcelas") {
        describe("Conocer la superficie del parcela") {
            val parcela = Parcela(720, 480, 14)
            it("Su superficie es 345600") {
                parcela.superficie().shouldBe(345600)
            }
        }
        describe("Conocer su Cantidad maxima de plantas que tolera") {
            val parcelaAnchoMayorQueLargo = Parcela(400, 200, 12)
            val parcelaAnchoMenorQueLargo = Parcela(100, 200, 12)
            it("Cantidad maxima de plantas que tolera, pero con ancho mayor que largo") {
                parcelaAnchoMayorQueLargo.cantidadMaximaPlantas().shouldBe(16000)
            }
            it("Cantidad maxima de plantas que tolera, pero con ancho menor que ancho") {
                parcelaAnchoMenorQueLargo.cantidadMaximaPlantas().shouldBe(6866)
            }
        }
        describe("Tiene Complicaciones?") {
            val parcelaConComplicacion = Parcela(100, 50, 7)
            val parcelaSinComplicacion = Parcela(10, 20, 4)

            val menta = Menta(2010, 1.7)
            val soja = Soja(2009, 1.5)
            val sojaTrasgenica = SojaTransgenica(2010, 0.4)
            it ("si tiene complicaciones, ya que menta, no soporta el sol de parcela") {
                parcelaConComplicacion.plantar(menta)
                parcelaConComplicacion.plantar(soja)
                parcelaConComplicacion.plantar(sojaTrasgenica)
                parcelaConComplicacion.tieneComplicaciones().shouldBeTrue()
            }
            it("no tiene complicaciones") {
                parcelaSinComplicacion.plantar(menta)
                parcelaSinComplicacion.plantar(soja)
                parcelaSinComplicacion.plantar(sojaTrasgenica)
                parcelaSinComplicacion.tieneComplicaciones().shouldBeFalse()
            }
        }
       // Si a esa parcela le plantamos 4 plantas de soja de más de 1 metro (que toleran 9 horas de sol), no tendría complicaciones.
       // Si intentaramos agregar una quinta planta, se superaría la cantidad máxima y nos arrojaría un error.
        describe("Agregar planta") {
            val parcela = Parcela(20, 1, 8)
            val soja1 = Soja(2008,1.2)
            val soja2 = Soja(2009, 1.6)
            val soja3 = Soja(2007, 1.8)
            val soja4 = Soja(2010, 2.4)
            val menta = Menta(2010, 1.7)

            it("Error, no puede agregar mas plantas") {
                parcela.plantar(soja1)
                parcela.plantar(soja2)
                parcela.plantar(soja3)
                parcela.plantar(soja4)
                shouldThrowAny { parcela.plantar(menta)}
            }
        }
    }
    describe("Testeo de Agricultor") {
        describe("El Agricultor tiene parcelas Semilleras?") {
            val parcelaSemillera = Parcela(20, 1, 5)
            val parcelaNoSemillera = Parcela(20,1,5)
            val menta = Menta(2010, 1.7)
            val soja = Soja(2009, 1.6)
            val sojaTrasgenica = SojaTransgenica(2010, 0.4)

            it("Hay 1 semillera") {
                parcelaSemillera.plantar(menta)
                parcelaSemillera.plantar(soja)
                val agricultor = Agricultora(mutableListOf(parcelaSemillera))
                agricultor.parcelasSemilleras().shouldContain(parcelaSemillera)
            }
            it("no hay semilleras") {
                parcelaNoSemillera.plantar(menta)
                parcelaNoSemillera.plantar(soja)
                parcelaNoSemillera.plantar(sojaTrasgenica)
                val agricultor = Agricultora(mutableListOf(parcelaNoSemillera))
                agricultor.parcelasSemilleras().shouldBeEmpty()
            }
            it("Plantar estratégicamente") {
                val agricultor = Agricultora(mutableListOf(parcelaSemillera,parcelaNoSemillera))
                parcelaSemillera.plantar(menta)
                agricultor.plantarEstrategicamente(soja)
                parcelaNoSemillera.plantas.shouldContain(soja)
            }
        }
    }
})
