DOC_DIR=doc
REPORT=main

.PHONY: watch_tex clean_tex clean_aux report

${REPORT}.pdf:
	cd ${DOC_DIR} && latexmk -shell-escape -pdf ${REPORT}.tex

clean_aux:
	cd ${DOC_DIR} && latexmk -c

clean_tex:
	cd ${DOC_DIR} && latexmk -C

watch_tex:
	cd ${DOC_DIR} && latexmk -pdf -pvc

report: ${REPORT}.pdf clean_aux
