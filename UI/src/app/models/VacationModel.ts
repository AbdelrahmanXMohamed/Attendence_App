import { VacationType } from "../utils/vacationType"

export interface VacationModel{

    id:number;

    userId:number

    username: string

    type: VacationType

    startDate :Date

    endDate:Date

    numberOfDay:number

    comment:string
}
