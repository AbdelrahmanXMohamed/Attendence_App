import {VacationType} from '../utils/vacationType'

export interface VacationRequest{

    type:VacationType

    startDate: Date 

    endDate:Date

    comment:string
}