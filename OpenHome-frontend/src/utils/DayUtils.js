export const getDay = (index)=>{
    switch(index){
      case 0: return "Sun";
      case 1: return "Mon";
      case 2: return "Tues";
      case 3: return "Wed";
      case 4: return "Thur";
      case 5: return "Fri";
      case 6: return "Sat";
    }
  }

export const getNumberOfWeekDaysAndWeekEnds = (start, end) => {
    let startDate = new Date(start);
    const endDate = new Date(end);

    const days = {
        numberOfWeekends:0,
        numberOfWeekdays:0
    }

    const milliSecondInOneDay = 24 * 60 * 60 * 1000;
    while (startDate.getTime() <= endDate.getTime()) {
        const day = startDate.getDay();
        if (day == 1 || day == 2 || day == 3 || day == 4) {
            days.numberOfWeekdays += 1;
        }
        else {
            days.numberOfWeekends += 1;
        }
        startDate = new Date(startDate.getTime() + milliSecondInOneDay);
    }

    return days;
}

export const getWeekRepresentation = (start, end) => {
    let startDate = new Date(start);
const endDate = new Date(end);

    let week = ["0","0","0","0","0","0","0"];
    
    const milliSecondInOneDay = 24 * 60 * 60 * 1000;
    const milliSecondInSevenDays = 24 * 60 * 60 * 1000*7;
    const pstDifference = 4*60*60*1000;
    if(new Date(startDate.getTime()+milliSecondInSevenDays) <= endDate.getTime()){
        return "1111111";
    }else{
      while (startDate.getTime() <= endDate.getTime()) {
        const day = startDate.getDay();
        week[day]=1;
        startDate = new Date(startDate.getTime() + milliSecondInOneDay);
    }
    return week.join("");
}
}