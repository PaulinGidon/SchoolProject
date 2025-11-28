########################################
####  ARGUMENTS 
##Ici on définie les variable global 
CFlags = -Wall -Wextra -Werror -lncurses
CC = gcc
NAME = jdlv
########################################
####   SOURCES
## Ici on crée deux variable avec les noms des fichiers , on crée les objets (OBJFILES) finissant par .o et et les code (SCRFILES)finissant par .c 
FILE = main\
       affiJeux \
       change \
       rand \
	   canon\
	   importTXT\
	   menu
       
SCRFILE = $(addsuffix .c, ${FILE})##crée les objets
OBJFILES = $(SCRFILE:.c=.o)##crée  les .c
########################################
####   RULES 
all: ${NAME} ##Debut du code principale du makfile

##Compile les objets et crée l'executable en prenant en compte les Flags 
${NAME}: ${OBJFILES}
	${CC} ${OBJFILES} -o ${NAME} ${CFlags} 

##prend tout ce qui l'y as avant le point .o et le mets comme nom du fichier et va faire correspondre au nom des fichier .c
%.o: %.c
	${CC} ${CFlags} -c $< -o $@
##Suprime les .o
clean:
	rm -f ${OBJFILES}

##Supprime tout ce que le makfile crée 
fclean: clean
	rm -f ${NAME}
##Suprrime tout ce que le makefile crée 
re: fclean all

##Definie all fclean clean et re comme des régle que l'on a écrit nous même pour ne pas le confondre avec d'autre fichiers  
.PHONY: all fclean clean re
