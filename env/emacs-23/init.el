;;----------------------------------
(add-to-list 'load-path "~/.emacs.d/")
(require 'package)
(add-to-list 'package-archives
             '("marmalade" . "http://marmalade-repo.org/packages/") t)
(package-initialize)

;;----------------------------------
(when (not package-archive-contents)
  (package-refresh-contents))

;; Add in your own as you wish:
(defvar my-packages '(starter-kit starter-kit-lisp starter-kit-ruby starter-kit-bindings)
  "A list of packages to ensure are installed at launch.")

(dolist (p my-packages)
  (when (not (package-installed-p p))
    (package-install p)))

;;----------------------------------
(global-set-key (kbd "C-c z") 'shell) 
(global-set-key (kbd "<f10>") 'rename-buffer)

;;---------------------------------
;Ctrl-c ←
(when (fboundp 'winner-mode) 
 (winner-mode) 
 (windmove-default-keybindings)) 