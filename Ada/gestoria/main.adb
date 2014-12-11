with Ada.Text_IO;
with Ada.Numerics.Discrete_Random;
use Ada.Text_IO;

procedure Main is
   type Espec is (NINGUNA, EMPRESA, PARTICULAR);

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
   function Generate_Boolean (Min : Integer;
                              Max : Integer) return Boolean is
      num:Natural;
      bool:Boolean;
   begin
      num := Generate_Number(MinValue => Min,
                                MaxValue => Max);
      if (num mod 2) = 0 then
         bool:=True;
      else
         bool:=False;
      end if;
      return bool;
   end Generate_Boolean;

   ------------------Fin funciones RANDOM----------------

   task Jefe is
   end;

   task Trabajadores is
      entry AtenderParticular;
      entry AtenderEmpresa;
      entry EspecialEmpresa;
      entry TerminarParticular(problemaResuelto: out Boolean);
      entry TerminarEmpresa(problemaResuelto: out Boolean);
      entry TrabajadorConJefe(ocupado: out Boolean);
      entry TempresaVacio(vacio: out Boolean);
      entry AtenderJefe;
      entry TerminarConJefe;
   end Trabajadores;

   task type Cliente is
      entry iniciar(N:Natural);
   end Cliente;


   task body Jefe is
      salir:Boolean:=False;
      aux:Boolean;
   begin
      loop
         Put_Line("El jefe esta llamando por telefono");
         salir:=False;
         loop
            aux:=Generate_Boolean(Min => 1,
                                  Max => 50);
            if not aux then
               Trabajadores.AtenderJefe;
               delay 6.0;
               Trabajadores.TerminarConJefe;
               salir:=True;
            end if;
            delay 10.0;
            exit when salir;
         end loop;
      end loop;
   end Jefe;
   task body Trabajadores is
      especEmpresa:Natural:=5;
      especParticular:Natural:=7;
      llamadaJefe:Boolean:=False;
      resuelto:Boolean;
   begin
      loop
         select
            when especParticular > 0 =>
               accept  AtenderParticular  do
                  especParticular := especParticular - 1;
               end AtenderParticular;
         or
            when especEmpresa > 0 =>
               accept AtenderEmpresa  do
                  especEmpresa := especEmpresa - 1;
               end AtenderEmpresa;
         or
            when especParticular > 0 =>
               accept EspecialEmpresa  do
                  especParticular := especParticular - 1;
               end EspecialEmpresa;
         or
            accept TerminarParticular (problemaResuelto : out Boolean) do
               resuelto := Generate_Boolean(Min => 0,
                                       Max => 10);
               problemaResuelto:=resuelto;
               especParticular := especParticular + 1;
            end TerminarParticular;
         or
            accept TerminarEmpresa (problemaResuelto : out Boolean) do
               resuelto := Generate_Boolean(Min => 0,
                                       Max => 10);
               problemaResuelto:=resuelto;
               especEmpresa := especEmpresa + 1;
            end TerminarEmpresa;
         or
            accept TempresaVacio (vacio : out Boolean) do
               if especEmpresa > 0 then
                  vacio:=False;
               else
                  vacio:=True;
               end if;
            end TEmpresaVacio;
         or
            accept TrabajadorConJefe (ocupado : out Boolean) do
               ocupado:=llamadaJefe;
            end TrabajadorConJefe;
         or
            when not llamadaJefe =>
               accept AtenderJefe  do
                  llamadaJefe := True;
               end AtenderJefe;
         or
            accept TerminarConJefe  do
               llamadaJefe := False;
            end TerminarConJefe;
         end select;

      end loop;
   end Trabajadores;

   task body Cliente is
      id:Natural;
      salir:Boolean;
      tipo:Espec:=NINGUNA;
      aux:Boolean;
   begin

      --Se genera antes el tipo de cliente por si tiene que repetir que sea con el mismo tipo
      aux:=Generate_Boolean(Min => 1,
                          Max => 50);
      if aux then
         tipo:=EMPRESA;
      else
         tipo:=PARTICULAR;
      end if;

      loop
      accept iniciar (N : in Natural) do
         id:=N;
      end iniciar;
      if tipo = PARTICULAR then
            Trabajadores.AtenderParticular;
            Put_Line("El particular " & id'Img &" esta siendo atendido.");
            Trabajadores.TrabajadorConJefe(ocupado => aux);
            if aux then
               Put_Line("Esperando que el trabajador acabe con su jefe.");
               loop
                        aux := True;
                        Trabajadores.TrabajadorConJefe(ocupado => aux);
                        exit when not aux;
                     end loop;
            end if;
         delay 5.0;
         --Si no ha resuelto el problema
            Trabajadores.TerminarParticular(problemaResuelto => salir);
            Put_Line("El particular  " & id'Img &" ha sido atendido.");
      else if tipo = EMPRESA then
            Trabajadores.TempresaVacio(vacio => aux);
            if aux then
                  Trabajadores.EspecialEmpresa;
                  Put_Line("No quedan trabajadores para empresa.");
                  Put_Line("Me atiende un trabajador para particulares.");
               Trabajadores.TrabajadorConJefe(ocupado => aux);
                  if aux then
                     Put_Line("Esperando que el trabajador acabe con su jefe.");
                     loop
                        aux := True;
                        Trabajadores.TrabajadorConJefe(ocupado => aux);
                        exit when not aux;
                     end loop;
                  end if;
               delay 10.0;
                  Trabajadores.TerminarParticular(problemaResuelto => salir);
                  Put_Line("La empresa  " & id'Img &" ha sido atendida por un particular.");
            else
                  Trabajadores.AtenderEmpresa;
                  Put_Line("La empresa  " & id'Img &" esta siendo atendida.");
               Trabajadores.TrabajadorConJefe(ocupado => aux);
                  if aux then
                     Put_Line("Esperando que el trabajador acabe con su jefe.");
                     loop
                        aux := True;
                        Trabajadores.TrabajadorConJefe(ocupado => aux);
                        exit when not aux;
                     end loop;
                  end if;
               delay 7.0;
                  Trabajadores.TerminarEmpresa(problemaResuelto => salir);
                  Put_Line("La empresa  " & id'Img &" ha sido atendida.");
               end if;
               if salir then
                  Put_Line("Su problema se ha resuelto correctamente y se puede ir");
               else
                  Put_Line("Su problema no se ha resuelto correctamente y tiene que volver en un tiempo");
                  delay 15.0;
               end if;
            end if;
      end if;
         exit when salir;
      end loop;
   end Cliente;
   type ClienteAccess is access Cliente;
   clientes: array (1..50) of ClienteAccess;

begin

   for i in 1..50 loop
      clientes(i) := new Cliente;
      clientes(i).iniciar(N => i);
   end loop;
end Main;
