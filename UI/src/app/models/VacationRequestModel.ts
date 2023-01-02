import { VacationStatus } from "../utils/vacationStatus"
import { VacationType } from "../utils/vacationType"

export interface VacationRequest{

    type:VacationType

    status:VacationStatus

    startDate:Date

    endDate:Date

    comment:string
}