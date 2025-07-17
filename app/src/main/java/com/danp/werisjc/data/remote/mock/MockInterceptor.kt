package com.danp.werisjc.data.remote.mock

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toString()
        val responseString = when {
            uri.endsWith("posts") -> postsResponse()
            uri.endsWith("services") -> servicesResponse()
            else -> "{}"
        }

        return Response.Builder()
            .code(200)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun postsResponse(): String {
        return """
    [
      {
        "id": 1,
        "label": "Promoción en Pollos",
        "message": "Disfruta del 2x1 en pollo a la brasa solo este fin de semana en El Buen Sabor.",
        "datetime": "2025-07-15T08:00:00Z",
        "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoaH9v4gPs3q9stemkD6E9S2Tmi6r2wbBT9Q&s",
        "serviceId": "svc001"
      },
      {
        "id": 2,
        "label": "Viaja más por menos",
        "message": "Pide tu medio pasaje mostrando tu DNI en Pollería El Buen Sabor durante Fiestas Patrias.",
        "datetime": "2025-07-14T10:00:00Z",
        "img": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/COP8MOP3_2006_Curitiba_bus_2.jpg/960px-COP8MOP3_2006_Curitiba_bus_2.jpg",
        "serviceId": "svc001"
      },
      {
        "id": 3,
        "label": "Menú Familiar Económico",
        "message": "Paquete familiar con papas y gaseosa por solo S/35 en El Buen Sabor.",
        "datetime": "2025-07-13T09:00:00Z",
        "img": "https://lacocinademasito.com/wp-content/uploads/2021/03/Menu-semanal-56.jpg",
        "serviceId": "svc001"
      },
      {
        "id": 4,
        "label": "Aniversario",
        "message": "Cafe gratis por nuestro aniversario.",
        "datetime": "2025-07-12T11:00:00Z",
        "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbhlKYz7RgMzQbJEhtXKnUHdbl5yqV8tbglw&s",
        "serviceId": "svc003"
      },
      {
        "id": 5,
        "label": "Campaña de Salud Gratuita",
        "message": "Consulta médica gratuita en Hospital Goyeneche este sábado.",
        "datetime": "2025-07-11T07:00:00Z",
        "img": "https://www.munilayo.gob.pe/wp-content/uploads/2023/10/Campana-de-salud-gratuita.webp",
        "serviceId": "svc004"
      },
      {
        "id": 6,
        "label": "Curso de Inglés Gratuito",
        "message": "Aprende inglés desde cero en Clínica San Pablo. Inscríbete ahora.",
        "datetime": "2025-07-10T10:00:00Z",
        "img": "https://www.munilayo.gob.pe/wp-content/uploads/2023/10/Campana-de-salud-gratuita.webp",
        "serviceId": "svc005"
      },
      {
        "id": 7,
        "label": "Taller de Seguridad",
        "message": "Capacitación gratuita sobre prevención del delito en Comisaría Yanahuara.",
        "datetime": "2025-07-09T16:00:00Z",
        "img": "https://pbs.twimg.com/media/GFIWOZUXgAAmSRQ?format=jpg&name=large",
        "serviceId": "svc006"
      },
      {
        "id": 8,
        "label": "Chequeo Dental Gratis",
        "message": "Promoción de limpieza dental gratuita por julio en Smile.",
        "datetime": "2025-07-08T08:00:00Z",
        "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLEmEGsTETjkGmjqhMUnP1-xtw0A7fQgOJzA&s",
        "serviceId": "svc007"
      },
      {
        "id": 9,
        "label": "Festival de Sabores",
        "message": "El Fogón ofrece menú típico con degustaciones gratis este domingo.",
        "datetime": "2025-07-07T15:00:00Z",
        "img": "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjwqdiOc0D1oBJbU2OXFCie0ToOhYlz1tusC43TluTn1_ieIdchoz_UdweYFOjukwemaTpto_x5rtN162ADYXXMRnGpHihKAJCxql8o1BqoH75w0Q5MDioT7DI5bu34iWW5RbP-pyXLVX-zqzfyRHJMNf1hg-wV6Nt_PV6o3fJwYbZBX7a07nyaLiGXRoQ/w1200-h630-p-k-no-nu/IMG-20230727-WA0105.jpg",
        "serviceId": "svc002"
      },
      {
        "id": 10,
        "label": "Trámites Gratuitos",
        "message": "Gestiones sin costo este sábado en Seguridad Privada Raptor.",
        "datetime": "2025-07-06T12:00:00Z",
        "img": "https://example.com/tramites.jpg",
        "serviceId": "svc009"
      },
      {
        "id": 11,
        "label": "Curso de Computación Básica",
        "message": "Aprende herramientas digitales en el Centro de Salud Paucarpata.",
        "datetime": "2025-07-05T09:00:00Z",
        "img": "https://example.com/computacion.jpg",
        "serviceId": "svc008"
      },
      {
        "id": 12,
        "label": "Cuidado para Mascotas",
        "message": "Lleva a tu mascota a vacunarse gratis en el Restaurante Municipal.",
        "datetime": "2025-07-04T11:00:00Z",
        "img": "https://example.com/veterinaria.jpg",
        "serviceId": "svc010"
      },
      {
        "id": 13,
        "label": "Campaña de Reciclaje",
        "message": "Recicla tus electrónicos este sábado en Lavandería CleanExpress.",
        "datetime": "2025-07-03T13:00:00Z",
        "img": "https://example.com/reciclaje.jpg",
        "serviceId": "svc011"
      },
      {
        "id": 14,
        "label": "Capacitación Agraria",
        "message": "Taller práctico de riego tecnificado en MiniMarket Centro.",
        "datetime": "2025-07-02T14:00:00Z",
        "img": "https://example.com/agricultura.jpg",
        "serviceId": "svc013"
      },
      {
        "id": 15,
        "label": "Fiesta Cultural en el Mercado",
        "message": "Música y danzas tradicionales en el Mercado San Camilo.",
        "datetime": "2025-07-01T17:00:00Z",
        "img": "https://img.freepik.com/vector-gratis/ilustracion-mercado-comida-callejera-diseno-plano_52683-118553.jpg?semt=ais_hybrid&w=740",
        "serviceId": "svc014"
      },
      {
        "id": 16,
        "label": "Salud Mental Gratuita",
        "message": "Atención psicológica sin costo en Lavandería Municipal de Arequipa.",
        "datetime": "2025-06-30T10:00:00Z",
        "img": "https://example.com/psicologia.jpg",
        "serviceId": "svc012"
      },
      {
        "id": 17,
        "label": "Reparación de Bicis",
        "message": "Taller de bicicletas gratuito en Comisaría Yanahuara.",
        "datetime": "2025-06-29T10:00:00Z",
        "img": "https://example.com/bicicleta.jpg",
        "serviceId": "svc006"
      },
      {
        "id": 18,
        "label": "Charla de Seguridad Ciudadana",
        "message": "Participa en nuestras charlas en Seguridad Privada Raptor.",
        "datetime": "2025-06-28T11:00:00Z",
        "img": "https://example.com/seguridad.jpg",
        "serviceId": "svc009"
      },
      {
        "id": 19,
        "label": "Refuerzo Escolar",
        "message": "Clases de apoyo gratuitas en Clínica San Pablo para escolares.",
        "datetime": "2025-06-27T09:00:00Z",
        "img": "https://example.com/refuerzo.jpg",
        "serviceId": "svc005"
      },
      {
        "id": 20,
        "label": "Bolsa de Trabajo Gratuita",
        "message": "Inscríbete y encuentra trabajo en el Centro de Salud Paucarpata.",
        "datetime": "2025-06-26T08:00:00Z",
        "img": "https://example.com/trabajo.jpg",
        "serviceId": "svc008"
      }
    ]
    """.trimIndent()
    }


    private fun servicesResponse(): String {
        return """
        [
          {
            "id": "svc001",
            "name": "Pollería El Buen Sabor",
            "address": "Av. Dolores 150, José Luis Bustamante",
            "latitude": -16.4220,
            "longitude": -71.5310,
            "description": "Especialidad en pollo a la brasa",
            "timetable": "Lunes a Domingo 12:00 - 22:00",
            "alert": "Sin alertas",
            "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtpbZATaDszKvX567oJ470u9tFtC3_44oMAg&s",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc002",
            "name": "Restaurante El Fogón",
            "address": "Av. Ejército 123, Cayma",
            "latitude": -16.3890,
            "longitude": -71.5480,
            "description": "Comida tradicional arequipeña",
            "timetable": "Todos los días 11:00 - 22:00",
            "alert": "Sin alertas",
            "img": "https://elfogon.com.uy/wp-content/uploads/2022/11/nosotros1.webp",
            "url": "https://chicha.com.pe/arequipa/"
          },
          {
            "id": "svc003",
            "name": "Café La Abuela",
            "address": "Calle San Francisco 456, Cercado",
            "latitude": -16.3985,
            "longitude": -71.5352,
            "description": "Café artesanal y pastelería",
            "timetable": "Lunes a Domingo 8:00 - 20:00",
            "alert": "Sin alertas",
            "img": "https://www.sillasmesas.es/blog/wp-content/uploads/2018/12/diferencia-licencia-pub-licencia-cafeteria-1.jpg",
            "url": "https://www.kaffeehaus.org/"
          },
          {
            "id": "svc004",
            "name": "Hospital Goyeneche",
            "address": "Av. Goyeneche s/n, Arequipa",
            "latitude": -16.4041,
            "longitude": -71.5375,
            "description": "Hospital general regional",
            "timetable": "Lunes a Sábado 7:00 - 20:00",
            "alert": "Sin alertas",
            "img": "https://elbuho.pe/wp-content/uploads/2023/05/Diseno-sin-titulo-9-1.jpg",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc005",
            "name": "Clínica San Pablo",
            "address": "Av. Victor Andrés Belaunde 120, Yanahuara",
            "latitude": -16.3875,
            "longitude": -71.5520,
            "description": "Atención médica privada",
            "timetable": "Lunes a Domingo 24h",
            "alert": "Sin alertas",
            "img": "https://diarioelpueblo.com.pe/wp-content/uploads/2023/01/3-san-pablo.jpg",
            "url": "https://www.hg.gob.pe/"
          },
          {
            "id": "svc006",
            "name": "Comisaría Yanahuara",
            "address": "Calle Los Arces 105, Yanahuara",
            "latitude": -16.3927,
            "longitude": -71.5449,
            "description": "Estación policial distrital",
            "timetable": "24/7",
            "alert": "Disponible",
            "img": "https://diariosinfronteras.com.pe/wp-content/uploads/2025/05/WhatsApp-Image-2025-05-18-at-10.59.11-PM.jpeg",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc007",
            "name": "Centro Odontológico Smile",
            "address": "Calle Sucre 89, Cercado",
            "latitude": -16.3990,
            "longitude": -71.5340,
            "description": "Consultorio dental privado",
            "timetable": "Lunes a Sábado 9:00 - 18:00",
            "alert": "Promoción de limpieza dental",
            "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTflH0i_m9rcyGSSdUgCCE_pGU8unFsiHgPRg&s",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc008",
            "name": "Centro de Salud Paucarpata",
            "address": "Av. Kennedy, Paucarpata",
            "latitude": -16.4263,
            "longitude": -71.5077,
            "description": "Atención médica y vacunas",
            "timetable": "Lunes a Viernes 8:00 - 17:00",
            "alert": "Campaña de vacunación",
            "img": "https://images.adsttc.com/media/images/5129/1a2c/b3fc/4b11/a700/5e52/large_jpg/1292433443-aitor-estevez-olaizola-2.jpg",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc009",
            "name": "Seguridad Privada Raptor",
            "address": "Av. Aviación 230, Cerro Colorado",
            "latitude": -16.3705,
            "longitude": -71.5250,
            "description": "Patrullaje y monitoreo privado",
            "timetable": "24/7",
            "alert": "Sin alertas",
            "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTss0yQlx2KnmJVx3cH_JyURvuB7e3vcYb7PA&s",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc010",
            "name": "Restaurante Municipal Arequipa",
            "address": "Av. Salaverry 123",
            "latitude": -16.3989,
            "longitude": -71.5340,
            "description": "Comedor popular con menú económico",
            "timetable": "Lunes a Viernes 12:00 - 15:00",
            "alert": "Sin alertas",
            "img": "https://media.istockphoto.com/id/857744820/es/vector/fachada-de-dise%C3%B1o-plano-del-vector-restaurante.jpg?s=612x612&w=0&k=20&c=w_40ns1HhwDqXx64lnjv_Drlha-H1L9gQ4vHXTSJ5mM=",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc011",
            "name": "Lavandería CleanExpress",
            "address": "Av. Dolores 321, José Luis Bustamante",
            "latitude": -16.4230,
            "longitude": -71.5300,
            "description": "Lavado y planchado de ropa",
            "timetable": "Lunes a Sábado 9:00 - 18:00",
            "alert": "Sin alertas",
            "img": "https://lavamistilavanderias.com/wp-content/uploads/2025/01/logotransparente2.png",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc012",
            "name": "Lavandería Municipal de Arequipa",
            "address": "Av. Pumacahua s/n",
            "latitude": -16.4210,
            "longitude": -71.5100,
            "description": "Lavandería comunal con atención social",
            "timetable": "Lunes a Viernes 9:00 - 17:00",
            "alert": "Sin alertas",
            "img": "https://lavamistilavanderias.com/wp-content/uploads/2025/01/logotransparente2.png",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc013",
            "name": "Tienda MiniMarket Centro",
            "address": "Jr. Piérola 201, Cercado",
            "latitude": -16.3980,
            "longitude": -71.5370,
            "description": "Productos básicos y snacks",
            "timetable": "Todos los días 7:00 - 23:00",
            "alert": "Sin alertas",
            "img": "https://shelficsoluciones.com/wp-content/uploads/2023/03/Fotografia-Real-Market-003.jpg",
            "url": "https://www.donbelisario.com.pe/"
          },
          {
            "id": "svc014",
            "name": "Mercado San Camilo",
            "address": "Calle Piérola, Cercado",
            "latitude": -16.3995,
            "longitude": -71.5378,
            "description": "Mercado público de abastos",
            "timetable": "Lunes a Domingo 6:00 - 18:00",
            "alert": "Reordenamiento interno",
            "img": "https://diariocorreo.pe/resizer/v2/LNQFQZSQQ5CTTKBPYST5MDPPMM.jpeg?auth=29f040eed0a45c147ca180f39e631eadbd4b44b4a89da19a3ad71b5621273d5e&width=1200&height=900&quality=75&smart=true",
            "url": "https://www.donbelisario.com.pe/"
          }
        ]
    """.trimIndent()
    }

}