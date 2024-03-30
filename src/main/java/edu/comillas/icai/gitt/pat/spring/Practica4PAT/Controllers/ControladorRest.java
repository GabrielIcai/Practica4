package edu.comillas.icai.gitt.pat.spring.Practica4PAT.Controllers;

import edu.comillas.icai.gitt.pat.spring.Practica4PAT.Excepciones.ExcepcionPedidoIncorrecto;
import edu.comillas.icai.gitt.pat.spring.Practica4PAT.Models.ModeloCampoIncorrecto;
import edu.comillas.icai.gitt.pat.spring.Practica4PAT.Models.Pedido;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@RestController
public class ControladorRest {
  private final Map<String, Pedido> pedidos = new HashMap<>();
  private final Map<String, Integer> modelos = new HashMap<>();

  {
    modelos.put("ModelX", 75000);
    modelos.put("ModelM", 65000);
    modelos.put("ModelS", 45500);
    modelos.put(null, 0);
  }
  private final Map<String, Integer> Extras = new HashMap<>();

  {
    Extras.put("Techo Solar", 2300);
    Extras.put("Asientos Calefactables", 1120);
    Extras.put("Pantalla", 700);
    Extras.put("Acabados Cuero", 5000);
    Extras.put(null, 0);
  }
  @PostMapping("/api/pedidos")
  @ResponseStatus(HttpStatus.CREATED)
    public Pedido creaPedido(@Valid @RequestBody Pedido pedido, BindingResult result){
    if (result.hasErrors()){
      throw new ExcepcionPedidoIncorrecto(result);
    }
    if (pedidos.containsKey(pedido.numero())){
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    pedidos.put(pedido.numero(),pedido);
    return pedido;
  }
  @GetMapping("/api/pedidos/{numero}")
  public Pedido buscaPedido(@PathVariable String numero){
    if (!pedidos.containsKey(numero)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return pedidos.get(numero);
  }
@GetMapping("/api/pedidos/{numero}/precio")
  public Integer CalculaPrecio(@PathVariable String numero){
  if (!pedidos.containsKey(numero)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    Pedido pedido_buscado=pedidos.get(numero);
    String Modelo_buscado= pedido_buscado.Modelo();
    String Extra1=pedido_buscado.Extra1();
    String Extra2=pedido_buscado.Extra2();
    int precio=modelos.get(Modelo_buscado)+Extras.get(Extra1)+Extras.get(Extra2);
    return precio;
}
@PutMapping("/api/pedidos/{numero}/modificar/{Extra1}")
  public Pedido modificaExtra(@PathVariable String numero, @PathVariable String Extra1){
    Pedido pedidoActual=pedidos.get(numero);
  if (!pedidos.containsKey(numero)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  if (pedidoActual != null) {
    Pedido pedidoModificado = new Pedido(
            pedidoActual.numero(),
            pedidoActual.Modelo(),
            pedidoActual.Color(),
            Extra1,
            pedidoActual.Extra2()
    );
    return pedidoModificado;
  } else {
    return null;
  }}

  @DeleteMapping("/api/pedidos/{numero}")
  public Map eliminar(@PathVariable String numero){
    pedidos.remove(numero);
    return pedidos;
  }
  @ExceptionHandler(ExcepcionPedidoIncorrecto.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<ModeloCampoIncorrecto> contadorIncorrecto(ExcepcionPedidoIncorrecto ex) {
    return ex.getErrores().stream().map(error -> new ModeloCampoIncorrecto(
            error.getDefaultMessage(), error.getField(), error.getRejectedValue()
    )).toList();
  }
  @Scheduled(fixedRate = 10000)
  public void actualizarNumeroPedidos() {
    int numeroPedidos = pedidos.size();
    System.out.println("Número de pedidos actualizado: " + numeroPedidos);
  }


}
