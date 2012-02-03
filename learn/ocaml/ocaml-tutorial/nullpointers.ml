(**)
(* Ocaml has an elegant solution to the problem of nulls, *)
(* using a simple polymorphic variant type defined (in Pervasives) as: *)
(**)

type 'a option = None | Some of 'a;;

Some 3;;

None;;

let a = None;;

let show it =
  let ret = match it with
  | None -> "none"
  | Some (v) -> v
  in
  print_endline ret
;;

show None;;
show (Some "some");;

assert (Sys.os_type = "Unix");;


