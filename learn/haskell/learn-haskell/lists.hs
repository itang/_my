module Main where
       size [] = 0
       size (h:t) = 1 + size t
       
       prod [] = 1
       prod (h:t) = h * prod t

       my_reverse :: [Integer] -> [Integer]
       my_reverse [] = []
       my_reverse (h:t) = reverse (t) : ( h :[] )
       r :: [Integer] ->[ Integer]
       r l = [ x | [x] <- my_reverse l]
