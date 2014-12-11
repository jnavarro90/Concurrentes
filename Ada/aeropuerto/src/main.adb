with Ada.Text_IO;
with Ada.Numerics.Discrete_Random;
use Ada.Text_IO;

procedure Main is
   IZQUIERDA: constant := 1;
   DERECHA: constant := 2;
   MAX_VIAJEROS: constant := 50;

   function Generate_Number (MaxValue : Integer) return Integer is
      subtype Random_Type is Integer range 0 .. MaxValue;
      package Random_Pack is new Ada.Numerics.Discrete_Random (Random_Type);
      G : Random_Pack.Generator;
   begin
      Random_Pack.Reset (G);
      return Random_Pack.Random (G);
   end Generate_Number;
   function Generate_Number (MinValue : Integer;
                             MaxValue : Integer) return Integer
   is
      subtype Random_Type is Integer range MinValue .. MaxValue;
      package Random_Pack is new Ada.Numerics.Discrete_Random (Random_Type);
      G : Random_Pack.Generator;
   begin
      Random_Pack.Reset (G);
      return Random_Pack.Random (G);

   end Generate_Number;

   protected Averia is
      procedure cintaAaveriada(averia: out Boolean);
      procedure cintaBaveriada(averia: out Boolean);
      procedure averiaEnCintaA;
      procedure averiaEnCintaB;
   private
      averiaA: Boolean := False;
      averiaB: Boolean := False;
   end Averia;
   protected body Averia is
      procedure averiaEnCintaA is
      begin
         averiaA := True;
         Ada.Text_IO.Put_Line("La cinta A se ha averiado");
      end averiaEnCintaA;
      procedure averiaEnCintaB is
      begin
         averiaB := True;
         Ada.Text_IO.Put_Line("La cinta B se ha averiado");
      end averiaEnCintaB;
      procedure cintaAaveriada(averia: out Boolean) is
      begin
         if averiaA then
            averia := True;
         else
            averiaA := False;
         end if;
      end cintaAaveriada;
      procedure cintaBaveriada(averia: out Boolean) is
      begin
         if averiaB then
            averia := True;
         else
            averiaB := False;
         end if;
      end cintaBaveriada;
   end Averia;
      protected CintaA is
      procedure estaEnMarcha(enMarcha: out Boolean);
      procedure arreglar;
      procedure parar;
      entry sensor;
      entry esperaTecnico;
      procedure error;
      procedure entrar;
      procedure salir;
      procedure estaAveriada(disponible: out Boolean);
      procedure pasaGente(pasando: out Boolean);
   private
      encendida:Boolean := False;
      averiada:Boolean := False;
      averiaB:Boolean:= False;
      Vpasando:Natural := 0;
   end CintaA;
   protected body CintaA is
      procedure estaEnMarcha(enMarcha: out Boolean) is
      begin
         if not encendida then
            enMarcha := false;
         else
            enMarcha := true;
         end if;
      end estaEnMarcha;
      procedure pasaGente(pasando: out Boolean) is
      begin
         if Vpasando = 0 then
            pasando := false;
         else
            pasando := true;
         end if;
      end pasaGente;
      procedure arreglar is
      begin
         averiada := false;
      end arreglar;
      procedure parar is
      begin
         Ada.Text_IO.Put_Line("Cinta B parada");
         encendida := false;
      end parar;
      procedure error is
      begin
         Averia.cintaBaveriada(averia => averiaB);
         if not averiaB then
            averiada := true;
            encendida := false;
            Averia.averiaEnCintaA;
         end if;
      end error;
      procedure entrar is
      begin
         Vpasando := Vpasando + 1;
      end entrar;
      procedure salir is
      begin
         Vpasando := Vpasando - 1;
      end salir;
      procedure estaAveriada (disponible: out Boolean)is
      begin
         if not averiada then
            disponible := False;
         else
            disponible := True;
         end if;
      end estaAveriada;
      entry sensor when not encendida is
      begin
         encendida := true;
      end sensor;
      entry esperaTecnico when not averiada is
      begin
         Ada.Text_IO.Put_Line("");
      end esperaTecnico;
   end CintaA;
   protected CintaB is
      procedure estaEnMarcha(enMarcha: out Boolean);
      procedure arreglar;
      procedure parar;
      entry sensor;
      entry esperaTecnico;
      procedure error;
      procedure entrar;
      procedure salir;
      procedure estaAveriada(disponible: out Boolean);
      procedure pasaGente(pasando: out Boolean);
   private
      encendida:Boolean :=False;
      averiada:Boolean := False;
      averiaA:Boolean:=False;
      Vpasando:Natural := 0;
   end CintaB;
   protected body CintaB is
      procedure estaEnMarcha(enMarcha: out Boolean) is
      begin
         if not encendida then
            enMarcha := false;
         else
            enMarcha := true;
         end if;
      end estaEnMarcha;
      procedure pasaGente(pasando: out Boolean) is
      begin
         if Vpasando = 0 then
            pasando := false;
         else
            pasando := true;
         end if;
      end pasaGente;
      procedure arreglar is
      begin
         averiada := false;
      end arreglar;
      procedure parar is
      begin
          Ada.Text_IO.Put_Line("Cinta B parada");
         encendida := false;
      end parar;
      procedure error is
      begin
         Averia.cintaAaveriada(averia => averiaA);
         if not averiaA then
            averiada := true;
            encendida := false;
            Averia.averiaEnCintaB;
         end if;
      end error;
      procedure entrar is
      begin
         Vpasando := Vpasando + 1;
      end entrar;
      procedure salir is
      begin
         Vpasando := Vpasando - 1;
      end salir;
      procedure estaAveriada (disponible: out Boolean)is
      begin
         if not averiada then
            disponible := False;
         else
            disponible := True;
         end if;
      end estaAveriada;
      entry sensor when not encendida is
      begin
         encendida := true;
      end sensor;
      entry esperaTecnico when not averiada is
      begin
         Ada.Text_IO.Put_Line("");
      end esperaTecnico;
   end CintaB;
   task type Viajero(sentido: Natural);
   task body Viajero is
      disponible: Boolean := false;
      averiada: Boolean := False;
   begin
      if sentido = IZQUIERDA then
         CintaA.estaEnMarcha(enMarcha => disponible);
         if not disponible then
            CintaA.estaAveriada(disponible => averiada);
            if averiada then
               CintaA.esperaTecnico;
            end if;
            CintaA.sensor;
            CintaA.entrar;
            Ada.Text_IO.Put_Line("Entra un viajero por la cinta A");
         else
            CintaA.entrar;
            Ada.Text_IO.Put_Line("Entra un viajero por la cinta A");
         end if;
         delay 15.0;
         CintaA.salir;
      else
         CintaB.estaEnMarcha(enMarcha => disponible);
         if not disponible then
            CintaB.estaAveriada(disponible => averiada);
            if averiada then
               CintaB.esperaTecnico;
            end if;
            CintaB.sensor;
            CintaB.entrar;
            Ada.Text_IO.Put_Line("Entra un viajero por la cinta B");
         else
            CintaB.entrar;
            Ada.Text_IO.Put_Line("Entra un viajero por la cinta B");
         end if;
         delay 15.0;
         CintaB.salir;
      end if;
   end Viajero;
   task type Tecnico;
   task body Tecnico is
      cintaAaveriada: Boolean := False;
      cintaBaveriada: Boolean := False;
   begin
   Ada.Text_IO.Put_Line("El Tecnico ha llegado");
      loop
         CintaA.estaAveriada(disponible => cintaAaveriada);
         CintaB.estaAveriada(disponible => cintaBaveriada);
         if cintaAaveriada or cintaBaveriada then
            if cintaAaveriada then
               CintaA.arreglar;
               Ada.Text_IO.Put_Line("El tecnico ha arreglado la cinta A");
            else
               CintaB.arreglar;
               Ada.Text_IO.Put_Line("El tecnico ha arreglado la cinta B");
            end if;
         end if;
      end loop;
   end Tecnico;
   task type Reloj;
   task body Reloj is
      ciclo: Natural := 0;
      cinta: Natural := 1;
      error: Natural := 0;
      cintaAencendida: Boolean := False;
      cintaBencendida: Boolean := False;
      pasandoCintaA: Boolean := False;
      pasandoCintaB: Boolean := False;
      ciclosCintaA: Natural := 0;
      ciclosCintaB: Natural := 0;
      tiempo: Natural := 0;
   begin
      Ada.Text_IO.Put_Line("El reloj se ha iniciado");
      loop
         error := Generate_Number(MinValue => 0,
                                MaxValue => 30);
         if (error mod 2) = 0 then
            if cinta = 1 then
               CintaA.error;
               cinta := 2;
            else
               CintaB.error;
               cinta := 1;
            end if;
         end if;
         ciclo := ciclo + 1;
         delay 5.0;
         CintaA.estaEnMarcha(enMarcha => cintaAencendida);
         CintaB.estaEnMarcha(enMarcha => cintaBencendida);
         CintaA.pasaGente(pasando => pasandoCintaA);
         CintaB.pasaGente(pasando => pasandoCintaB);
         if cintaAencendida and not pasandoCintaA then
            if ciclosCintaA = 30 then
               CintaA.parar;
               ciclosCintaA := 0;
            end if;
            ciclosCintaA := ciclosCintaA + 1;
         else if cintaBencendida and not pasandoCintaB then
               if ciclosCintaB = 30 then
                  CintaB.parar;
                  ciclosCintaB := 0;
               end if;
               ciclosCintaB := ciclosCintaB + 1;
            end if;
         end if;
      end loop;
   end Reloj;

   type TecnicoAccess is access Tecnico;
   ObjetoTecnico: TecnicoAccess;
   type RelojAccess is access Reloj;
   ObjetoReloj: RelojAccess;
   type ViajeroAccess is access Viajero;
   Viajeros: array(1..MAX_VIAJEROS) of ViajeroAccess;
   sentido:Natural := 0;
   tiempo: Natural := 0;
begin
   ObjetoReloj := new Reloj;
   ObjetoTecnico := new Tecnico;
   for i in 1..MAX_VIAJEROS loop
      sentido := Generate_Number(MinValue => 1,
                                 MaxValue => 2);
      Viajeros(i) := new Viajero(sentido);
      tiempo := Generate_Number(MinValue => 0,
                                MaxValue => 5);

      delay Duration(tiempo);
   end loop;
end Main;

