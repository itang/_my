open List
let len = length [1;2;3];;
print_int len;;

open Printf
let my_data = ["a"; "beautiful"; "day"];;
List.iter(fun s -> printf "%s\n" s) my_data;;

(* Interfaces and signatures *)

(* Abstract types *)

(* Submodule implementation *)
module Hello = 
struct
  let message = "Hello"
  let hello () = print_endline message
end;;

(* Submodule interface *)
module Hello1 : 
sig
 val hello : unit -> unit
end = 
struct
  let message = "Hello"
  let hello () = print_endline message
end

(* Functors *)




