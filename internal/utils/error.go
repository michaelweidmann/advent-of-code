package utils

import "log"

func HandleErrorFatally(err error) {
	if err != nil {
		log.Fatalln("unexpected error occurred:", err)
	}
}
